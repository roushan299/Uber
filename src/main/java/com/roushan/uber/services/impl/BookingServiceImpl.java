package com.roushan.uber.services.impl;

import com.roushan.uber.dto.BookingRequest;
import com.roushan.uber.dto.BookingResponse;
import com.roushan.uber.entity.Booking;
import com.roushan.uber.entity.Driver;
import com.roushan.uber.entity.Passenger;
import com.roushan.uber.enums.BookingStatus;
import com.roushan.uber.mapper.BookingMapper;
import com.roushan.uber.repository.BookingRepository;
import com.roushan.uber.repository.DriverRepository;
import com.roushan.uber.repository.PassengerRepository;
import com.roushan.uber.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> findByPassengerId(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with id: "+passengerId));
        return bookingRepository.findByPassenger(passenger)
                .stream()
                .map(bookingMapper::toResponse)
                .collect( Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> findByDriverId(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: "+driverId));
        return bookingRepository.findByDriver(driver)
                .stream()
                .map(bookingMapper::toResponse)
                .collect( Collectors.toList());
    }

    @Override
    public BookingResponse updateStatus( Long id, BookingStatus bookingStatus ) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not fond with id: "+id));

        booking.setBookingStatus(bookingStatus);
        if(bookingStatus == BookingStatus.IN_PROGRESS && booking.getActualPickUpTime() == null){
            booking.setActualPickUpTime( LocalDateTime.now());
        }else if (bookingStatus == BookingStatus.COMPLETED) {
            booking.setCompletedAt(LocalDateTime.now());

            if(booking.getDriver() != null){
                Driver driver = booking.getDriver();
                driver.setIsAvailable(true);
                driverRepository.save(driver);
            }
        }else if (bookingStatus == BookingStatus.CANCELLED) {
            if(booking.getDriver() != null){
                Driver driver = booking.getDriver();
                driver.setIsAvailable(true);
                driverRepository.save(driver);
            }
        }

        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(updatedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingResponse> findById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> findAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect( Collectors.toList());
    }

    @Override
    public BookingResponse create(BookingRequest request) {
        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with id: "+request.getPassengerId()));

        Driver driver = null;
        if(request.getDriverId() != null){
            driver = driverRepository.findById(request.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: "+request.getDriverId()));

            if(!driver.getIsAvailable()){
                throw new IllegalArgumentException("Driver with id "+request.getDriverId()+" is not available");
            }
        }

        Booking booking = bookingMapper.toEntity(request, passenger, driver);

        if(driver != null){
            driver.setIsAvailable(true);
            driverRepository.save(driver);
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public BookingResponse update(Long id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not fond with id: "+id));

        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with id: "+request.getPassengerId()));

        Driver driver = null;
        if(request.getDriverId() != null){
            driver = driverRepository.findById(request.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: "+request.getDriverId()));
        }

        Driver previousDriver = booking.getDriver();
        if(previousDriver != null && previousDriver.equals(driver)){
            previousDriver.setIsAvailable(true);
            driverRepository.save(previousDriver);
        }

        if(driver != null && !driver.equals(previousDriver)){
            if(!driver.getIsAvailable()){
                throw new IllegalArgumentException("Driver with id "+request.getDriverId()+" is not available");
            }
            driver.setIsAvailable(false);
            driverRepository.save(driver);
        }

        bookingMapper.updateEntity(booking, request, passenger, driver);
        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(updatedBooking);
    }

    @Override
    public void deleteById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not fond with id: "+id));

        if(booking.getDriver() != null){
            Driver driver = booking.getDriver();
            driver.setIsAvailable(true);
            driverRepository.save(driver);
        }
        bookingRepository.deleteById(id);
    }

}
