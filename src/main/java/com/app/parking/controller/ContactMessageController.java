package com.app.parking.controller;


import com.app.parking.dto.ContactMessageCreateRequest;
import com.app.parking.entity.ContactMessage;
import com.app.parking.entity.Reservation;
import com.app.parking.services.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact")
public class ContactMessageController {

    private final ContactMessageService service;

    @PostMapping
    public ContactMessage create(@Valid @RequestBody ContactMessageCreateRequest request) {
        return service.createContactMessage(request);
    }

    @GetMapping("/admin")
    public List<ContactMessage> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/admin/{id}")
    public ContactMessage updateContactMessage(@PathVariable Long id, @RequestBody ContactMessageCreateRequest contactMessageRequest) {
        return service.update(id, contactMessageRequest);
    }
}
