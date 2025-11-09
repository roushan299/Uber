package com.roushan.uber.services;

import com.roushan.uber.dto.BookingResponse;
import java.util.List;

public interface BookingReadService extends ReadService<BookingResponse, Long> {

    List<BookingResponse> findByPassengerId(Long passengerId);
    List<BookingResponse> findByDriverId(Long driverId);

}
