package com.app.parking.services;

import com.app.parking.dto.AvailabilityRequest;
import com.app.parking.dto.AvailabilityResponse;
import com.app.parking.entity.Reservation;
import com.app.parking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final ReservationRepository reservationRepository;
    private final PricingService pricingService;
    private final SettingService settingsService;

    public AvailabilityResponse checkAvailability(AvailabilityRequest req) {

        LocalDateTime start = req.startTime().toLocalDateTime();
        LocalDateTime end = req.endTime().toLocalDateTime();

        // Validate
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time cannot be before start time.");
        }

        // Find overlapping reservations
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(start, end);

        int capacity = settingsService.getParkingCapacity();
        int occupied = overlapping.size();
        int free = capacity - occupied;

        boolean available = free > 0;

        double price = pricingService.calculatePrice(start, end);
        long days = pricingService.calculateDays(start, end);

        return new AvailabilityResponse(
                available,
                free,
                occupied,
                price,
                days
        );
    }
}