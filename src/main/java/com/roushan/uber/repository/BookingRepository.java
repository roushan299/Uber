package com.roushan.uber.repository;

import com.roushan.uber.entity.Booking;
import com.roushan.uber.entity.Driver;
import com.roushan.uber.entity.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository {

    List<Booking> findByPassenger(Passenger passenger);
    List<Booking> findByDriver(Driver driver);
    List<Booking> findByIdAndPassenger(Long id, Passenger passenger);
    List<Booking> findByIdAndDriver(Long id, Driver driver);

}
