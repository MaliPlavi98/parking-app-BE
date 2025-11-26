package com.app.parking.repository;

import com.app.parking.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Setting, Long> {

    Optional<Setting> findByKeyName(String keyName);
}
