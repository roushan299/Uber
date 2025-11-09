package com.roushan.uber.controller;

import com.roushan.uber.dto.BookingRequest;
import com.roushan.uber.dto.BookingResponse;
import com.roushan.uber.enums.BookingStatus;
import com.roushan.uber.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookingResponse> bookings = bookingService.findAll();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id){
        return bookingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<BookingResponse>> getAllBookingsByPassenger(@PathVariable Long passengerId){
       try {
           List<BookingResponse> bookings = bookingService.findByPassengerId(passengerId);
           return ResponseEntity.ok(bookings);
       }catch (IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<BookingResponse>> getAllBookingsByDriver(@PathVariable Long driverId){
        try {
            List<BookingResponse> bookings = bookingService.findByDriverId(driverId);
            return ResponseEntity.ok(bookings);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest){
        try {
            BookingResponse booking = bookingService.create(bookingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingRequest bookingRequest){
        try {
            BookingResponse booking = bookingService.update(id, bookingRequest);
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status){
        try {
            BookingResponse booking = bookingService.updateStatus(id, status);
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        try {
            bookingService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
