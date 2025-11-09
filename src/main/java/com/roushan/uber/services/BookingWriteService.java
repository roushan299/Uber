package com.roushan.uber.services;

import com.roushan.uber.dto.BookingRequest;
import com.roushan.uber.dto.BookingResponse;
import com.roushan.uber.enums.BookingStatus;

public interface BookingWriteService extends WriteService<BookingResponse, BookingRequest, Long> {
    BookingResponse updateStatus(Long id, BookingStatus bookingStatus);

}
