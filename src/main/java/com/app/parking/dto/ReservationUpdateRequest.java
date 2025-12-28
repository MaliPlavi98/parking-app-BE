package com.app.parking.dto;

import com.app.parking.util.enums.ReservationStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ReservationUpdateRequest(
        @Size(max = 255) String name,
        @Size(max = 255) String email,
        @Size(max = 50) String phone,
        @Size(max = 20) String carPlate,
        @Size(max = 20) String returnFlightNumber,
        @Min(1) @Max(10) Integer passengers,
        @Size(max = 1024) String details,
        ReservationStatus reservationStatus
) {
}

