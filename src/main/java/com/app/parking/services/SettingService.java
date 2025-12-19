package com.app.parking.services;

import com.app.parking.dto.SettingRequest;
import com.app.parking.entity.Setting;
import com.app.parking.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

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
    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    public Setting create(SettingRequest request) {

        // TODO Add logic of creating setting

        return settingRepository.save(null);
    }

    public Setting update(Long id, SettingRequest settingRequest) {

        // TODO Add logic for updating setting

        return settingRepository.save(null);
    }

    public void delete(Long id) {
        settingRepository.deleteById(id);
    }


}
