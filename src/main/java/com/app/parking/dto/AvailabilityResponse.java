package com.app.parking.dto;

public record AvailabilityResponse(
        boolean available,
        int freeSpots,
        int occupiedSpots,
        double totalPrice,
        long totalDays
) {
}
