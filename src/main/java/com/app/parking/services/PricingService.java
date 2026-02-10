package com.app.parking.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final SettingService settingService;

    public double calculatePrice(Instant start, Instant end) {

        double dailyPrice = settingService.getDailyPrice();
        double firstDayPrice = settingService.getFirstDayPrice();

        long days = ChronoUnit.DAYS.between(start, end);

        // Minimum 1 day
        if (days <= 0) {
            days = 1;
        }

        // First day has special price
        if (days == 1) {
            return firstDayPrice;
        }

        // First day + remaining days
        return firstDayPrice + (days - 1) * dailyPrice;
    }

    public long calculateDays(Instant start, Instant end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End before start");
        }

        long minutes = Duration.between(start, end).toMinutes();
        return (long) Math.ceil(minutes / 1440.0);
    }

}
