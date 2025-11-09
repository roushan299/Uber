package com.roushan.uber.dto;

import com.roushan.uber.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;
    private Long passengerId;
    private String passengerName;
    private Long driverId;
    private String driverName;
    private String pickUpLocation;
    private String dropOffLocation;
    private BookingStatus status;
    private BigDecimal fare;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime scheduledPickUpTime;
    private LocalDateTime actualPickUpTime;
    private LocalDateTime completedAt;
}
