package com.roushan.uber.repository;

import com.roushan.uber.entity.Passenger;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PassengerRepository {
    Optional<Passenger> findByEmail(String email);
    boolean exitsByEmail(String email);
}
