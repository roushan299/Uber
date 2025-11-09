package com.roushan.uber.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class BookingRequest {
    @NotNull(message = "Passenger Id is required")
    private Long passengerId;

    private Long driverId;

    @NotBlank(message = "Pick up location is required")
    private String pickUpLocation;

    @NotBlank(message = "Drop off location is required")
    private String dropOffLocation;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be positive")
    private BigDecimal fare;

    private LocalDateTime scheduledPickUpTime;
}
