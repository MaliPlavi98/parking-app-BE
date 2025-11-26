package com.app.parking.security.service;

import com.app.parking.entity.Setting;
import com.app.parking.repository.SettingRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    SettingRepository settingRepository;

    public int getParkingCapacity() {
        return Integer.parseInt(
                settingRepository.findByKeyName("PARKING_CAPACITY")
                        .orElseThrow(() -> new RuntimeException("Missing setting: PARKING_CAPACITY"))
                        .getValue()
        );
    }

    public double getDailyPrice() {
        return Double.parseDouble(
                settingRepository.findByKeyName("DAILY_PRICE")
                        .orElseThrow(() -> new RuntimeException("Missing setting: DAILY_PRICE"))
                        .getValue()
        );
    }

    // Add update method here:
    public void updateSetting(String key, String value) {
        Setting setting = settingRepository.findByKeyName(key)
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        setting.setValue(value);
        settingRepository.save(setting);
    }

}
