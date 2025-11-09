package com.roushan.uber.services;

import com.roushan.uber.dto.DriverResponse;
import java.util.List;
import java.util.Optional;

public interface DriverReadService extends ReadService<DriverResponse, Long> {

    Optional<DriverResponse> findByIdEmail(String email);
    List<DriverResponse> findAvailableDrivers();
}
