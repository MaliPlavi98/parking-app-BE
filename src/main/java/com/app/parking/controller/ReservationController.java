package com.app.parking.controller;

import com.app.parking.dto.AvailabilityRequest;
import com.app.parking.dto.AvailabilityResponse;
import com.app.parking.dto.ReservationCreateRequest;
import com.app.parking.entity.Reservation;
import com.app.parking.services.AvailabilityService;
import com.app.parking.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AvailabilityService availabilityService;

    @PostMapping
    public Reservation create(@Valid @RequestBody ReservationCreateRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }

    @PostMapping("/check")
    public AvailabilityResponse checkAvailability(
            @Valid @RequestBody AvailabilityRequest request) {

        if (request.endTime().isBefore(request.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        
        return availabilityService.checkAvailability(request);
    }

}
