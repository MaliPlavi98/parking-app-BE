package com.app.parking.dto;

import jakarta.validation.constraints.NotNull;


import java.time.OffsetDateTime;

public record AvailabilityRequest(
        @NotNull OffsetDateTime startTime,
        @NotNull OffsetDateTime endTime) {
}
