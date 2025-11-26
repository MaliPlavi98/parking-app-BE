package com.app.parking.entity;

import com.app.parking.util.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Pricing
    private double totalPrice;

    // Shuttle
    private boolean shuttleRequested;

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
