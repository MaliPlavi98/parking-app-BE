package com.app.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationCreateRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String phone,

        // OPTIONAL
        String carPlate,
        String returnFlightNumber,
        Integer passengers,
        Boolean shuttleRequested,

        // REQUIRED
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime
) {
}

