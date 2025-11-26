package com.app.parking.dto;

import jakarta.validation.constraints.*;

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

        @Size(max = 20)
        String returnFlightNumber,

        @Min(1) @Max(10)
        Integer passengers,

        Boolean shuttleRequested,

        @NotNull
        LocalDateTime startTime,

        @NotNull
        LocalDateTime endTime
) {
}

