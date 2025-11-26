package com.app.parking.services;

import com.app.parking.dto.ContactMessageCreateRequest;
import com.app.parking.entity.ContactMessage;
import com.app.parking.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository repository;

    public ContactMessage createContactMessage(ContactMessageCreateRequest request) {

        ContactMessage cm = new ContactMessage();
        cm.setName(request.name());
        cm.setEmail(request.email());
        cm.setPhone(request.phone());
        cm.setMessage(request.message());
        cm.setTitle(request.title());
        cm.setCreatedAt(LocalDateTime.now());

        return repository.save(cm);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<ContactMessage> getAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public List<ContactMessage> searchByEmail(String email) {
        return repository.findByEmailContainingIgnoreCase(email);
    }

    public List<ContactMessage> searchByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.findByCreatedAtBetween(start, end);
    }

    public List<ContactMessage> searchByKeyword(String keyword) {
        return repository.findByMessageContainingIgnoreCase(keyword);
    }
}