package com.app.parking.services;

import com.app.parking.dto.ReservationCreateRequest;
import com.app.parking.dto.ReservationUpdateRequest;
import com.app.parking.entity.Reservation;
import com.app.parking.repository.ReservationRepository;
import com.app.parking.util.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    public Reservation createReservation(@NotNull ReservationCreateRequest req) {

        Reservation reservation = new Reservation();

        reservation.setName(req.name());
        reservation.setEmail(req.email());
        reservation.setPhone(req.phone());

        // Optional fields
        reservation.setCarPlate(req.carPlate());
        reservation.setReturnFlightNumber(req.returnFlightNumber());
        reservation.setPassengers(req.passengers());
        reservation.setDetails(req.details());
        reservation.setTotalPrice(req.totalPrice());

        // Required fields
        reservation.setStartTime(req.startTime());
        reservation.setEndTime(req.endTime());

        return repository.save(reservation);
    }

    public Reservation updateReservation(Long id, @NotNull ReservationUpdateRequest req) {

        Reservation reservation = repository.findById(id)
                                            .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setName(req.name());
        reservation.setEmail(req.email());
        reservation.setPhone(req.phone());

        // Optional fields
        reservation.setCarPlate(req.carPlate());
        reservation.setReturnFlightNumber(req.returnFlightNumber());
        reservation.setPassengers(req.passengers());
        reservation.setDetails(req.details());
        reservation.setStatus(req.reservationStatus());

        return repository.save(reservation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Reservation getReservationById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public List<Reservation> findOverlappingReservations(Instant start, Instant end) {
        return repository.findOverlappingReservations(start, end);
    }

    public List<Reservation> findByStatus(ReservationStatus status) {
        return repository.findByStatus(status);
    }

    public List<Reservation> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
