package com.app.parking.entity;

import com.app.parking.util.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User contact details (no account needed)
    private String name;
    private String email;
    private String phone;

    // Vehicle
    private String carPlate;

    // Flight info
    private String returnFlightNumber;

    private int passengers;

    // Reservation time window
    private Instant startTime;
    private Instant endTime;

    // Pricing
    private double totalPrice;

    // Details
    private String details;

    // Reservation status
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // Date created
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = ReservationStatus.PENDING;
    }
}
