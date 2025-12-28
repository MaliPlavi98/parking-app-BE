package com.app.parking.controller;

import com.app.parking.dto.SettingRequest;
import com.app.parking.entity.Setting;
import com.app.parking.services.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    // GET all settings
    @GetMapping
    public List<Setting> getAllSettings() {
        return settingService.getAll();
    }

    // GET all settings
    @GetMapping("/{key}")
    public Setting getSettingByKeyName(@PathVariable String key) {
        return settingService.getSettingByKeyName(key);
    }

    // CREATE a new setting
    @PostMapping
    public Setting createSetting(@RequestBody SettingRequest request) {
        return settingService.create(request);
    }

    // UPDATE a setting by ID
    @PutMapping("/{id}")
    public Setting updateSetting(
            @PathVariable Long id,
            @RequestBody SettingRequest request) {
        return settingService.update(id, request);
    }

    // DELETE a setting by ID
    @DeleteMapping("/{id}")
    public void deleteSetting(@PathVariable Long id) {
        settingService.delete(id);
    }
}
