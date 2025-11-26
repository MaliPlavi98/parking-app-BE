package com.app.parking.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final SettingService settingService;

    public double calculatePrice(LocalDateTime start, LocalDateTime end) {

        long days = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());

        // Minimum 1 day
        if (days == 0) {
            days = 1;
        }

        return days * settingService.getDailyPrice();
    }

    public long calculateDays(LocalDateTime start, LocalDateTime end) {
        long days = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
        return days == 0 ? 1 : days;
    }
}
