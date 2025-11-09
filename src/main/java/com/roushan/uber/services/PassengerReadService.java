package com.roushan.uber.services;

import com.roushan.uber.dto.PassengerResponse;
import java.util.Optional;

public interface PassengerReadService extends ReadService<PassengerResponse, Long> {

    Optional<PassengerResponse> findByEmail(String email);

}
