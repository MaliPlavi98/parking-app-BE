package com.app.parking.services;

import com.app.parking.dto.ReservationCreateRequest;
import com.app.parking.entity.Reservation;
import com.app.parking.repository.ReservationRepository;
import com.app.parking.util.enums.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    public Reservation createReservation(ReservationCreateRequest req) {

        Reservation reservation = new Reservation();

        reservation.setName(req.name());
        reservation.setEmail(req.email());
        reservation.setPhone(req.phone());

        // Optional fields
        reservation.setCarPlate(req.carPlate());
        reservation.setReturnFlightNumber(req.returnFlightNumber());
        reservation.setPassengers(req.passengers());
        reservation.setShuttleRequested(req.shuttleRequested());

        // Required fields
        reservation.setStartTime(req.startTime());
        reservation.setEndTime(req.endTime());

        return repository.save(reservation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Reservation> findOverlappingReservations(LocalDateTime start, LocalDateTime end) {
        return repository.findOverlappingReservations(start, end);
    }

    public List<Reservation> findByStatus(ReservationStatus status) {
        return repository.findByStatus(status);
    }

    public List<Reservation> getAll() {
        return repository.findAll();
    }
}
