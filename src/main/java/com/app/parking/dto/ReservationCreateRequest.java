package com.app.parking.dto;

import com.app.parking.util.enums.ReservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.time.LocalDateTime;

public record ReservationCreateRequest(
        @NotBlank
        @Size(max = 255)
        String name,

        @NotBlank
        @Size(max = 255)
        String email,

        @NotBlank
        @Size(max = 50)
        String phone,

        @Size(max = 20)
        String carPlate,

        @NotNull
        @Digits(integer = 10, fraction = 2)
        Double totalPrice,

        @Size(max = 20)
        String returnFlightNumber,

        @Min(1) @Max(10)
        Integer passengers,

        @Size(max = 1024)
        String details,

        @NotNull
        Instant startTime,

        @NotNull
        Instant endTime
) {
}

