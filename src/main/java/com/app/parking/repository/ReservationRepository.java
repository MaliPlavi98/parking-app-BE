package com.app.parking.repository;

import com.app.parking.entity.Reservation;
import com.app.parking.util.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.startTime < :end
              AND r.endTime > :start
            """)
    List<Reservation> findOverlappingReservations(Instant start, Instant end);

    List<Reservation> findByStatus(ReservationStatus status);

}
