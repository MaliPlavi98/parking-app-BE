package com.app.parking.repository;

import com.app.parking.entity.Reservation;
import com.app.parking.util.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    @Query("SELECT r FROM ParkingReservation r WHERE r.start < :end AND r.end > :start")
    List<Reservation> findOverlappingReservations(LocalDateTime start, LocalDateTime end);

    boolean existsByParkingSpotIdAndStartBeforeAndEndAfter(
            Long spotId, LocalDateTime end, LocalDateTime start
    );

    List<Reservation> findByStatus(ReservationStatus status);

}
