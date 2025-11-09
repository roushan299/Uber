package com.roushan.uber.mapper;

import com.roushan.uber.dto.BookingRequest;
import com.roushan.uber.dto.BookingResponse;
import com.roushan.uber.entity.Booking;
import com.roushan.uber.entity.Driver;
import com.roushan.uber.entity.Passenger;
import com.roushan.uber.enums.BookingStatus;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity( BookingRequest request, Passenger passenger, Driver driver){
        BookingStatus status = driver != null ? BookingStatus.CONFIRMED : BookingStatus.PENDING;
        Booking booking = Booking.builder()
                .passenger(passenger)
                .driver(driver)
                .pickupLocation(request.getPickUpLocation())
                .dropOffLocation(request.getDropOffLocation())
                .fare(request.getFare())
                .bookingStatus(status)
                .scheduledPickUpTime(request.getScheduledPickUpTime())
                .build();
        return booking;
    }

    public BookingResponse toResponse(Booking booking){
        BookingResponse response = BookingResponse.builder()
                .id(booking.getId())
                .passengerId(booking.getPassenger() != null ? booking.getPassenger().getId() : null)
                .passengerName(booking.getPassenger() != null ? booking.getPassenger().getName() : null)
                .driverId(booking.getDriver() != null ? booking.getDriver().getId() : null)
                .driverName(booking.getDriver() != null ? booking.getDriver().getName() : null)
                .pickUpLocation(booking.getPickupLocation())
                .dropOffLocation(booking.getDropOffLocation())
                .status(booking.getBookingStatus())
                .fare(booking.getFare())
                .createAt(booking.getCreationAt())
                .updateAt(booking.getUpdateAt())
                .scheduledPickUpTime(booking.getScheduledPickUpTime())
                .actualPickUpTime(booking.getActualPickUpTime())
                .completedAt(booking.getCompletedAt())
                .build();
        return response;
    }

    public void updateEntity(Booking booking, BookingRequest request, Passenger passenger, Driver driver){
        booking.setPassenger(passenger);
        booking.setDriver(driver);
        booking.setPickupLocation(request.getPickUpLocation());
        booking.setDropOffLocation(request.getDropOffLocation());
        booking.setFare(request.getFare());
        booking.setScheduledPickUpTime(request.getScheduledPickUpTime());

        if(driver != null && booking.getBookingStatus() == BookingStatus.PENDING){
            booking.setBookingStatus(BookingStatus.CONFIRMED);
        }
    }
}
