package com.app.parking.controller;

import com.app.parking.security.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @PutMapping("/{key}")
    public void updateSetting(
            @PathVariable String key,
            @RequestBody String value) {
        settingService.updateSetting(key, value);
    }
}
