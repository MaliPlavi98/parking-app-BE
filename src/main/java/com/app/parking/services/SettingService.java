package com.app.parking.services;

import com.app.parking.dto.SettingRequest;
import com.app.parking.entity.Setting;
import com.app.parking.repository.SettingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Setting setting = new Setting();

        setting.setKeyName(request.key());
        setting.setValue(request.value());

        return settingRepository.save(setting);
    }

    public Setting update(Long id, SettingRequest settingRequest) {
        return settingRepository.findById(id)
                                .map(setting -> {
                                    setting.setKeyName(settingRequest.key());
                                    setting.setValue(settingRequest.value());
                                    return settingRepository.save(setting);
                                })
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Setting not found with id: " + id
                                ));
    }

    public void delete(Long id) {
        settingRepository.deleteById(id);
    }

    public Setting getSettingByKeyName(String key) {
        return settingRepository.findByKeyName(key)
                                .orElseThrow(() -> new RuntimeException("Setting not available"));

    }
}
