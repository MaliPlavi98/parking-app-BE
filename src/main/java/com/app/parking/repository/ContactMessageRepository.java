package com.app.parking.repository;

import com.app.parking.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    
    // Pretraga po datumu
    List<ContactMessage> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // Pretraga po emailu
    List<ContactMessage> findByEmailContainingIgnoreCase(String email);

    // Pretraga po imenu
    List<ContactMessage> findByNameContainingIgnoreCase(String name);

    // Pretraga po keywordu u poruci
    List<ContactMessage> findByMessageContainingIgnoreCase(String keyword);

    // Lista svih, sortirana po datumu (najnovije prve)
    List<ContactMessage> findAllByOrderByCreatedAtDesc();

}
