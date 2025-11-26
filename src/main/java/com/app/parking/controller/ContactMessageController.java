package com.app.parking.controller;


import com.app.parking.dto.ContactMessageCreateRequest;
import com.app.parking.entity.ContactMessage;
import com.app.parking.services.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService service;

    @PostMapping
    public ContactMessage create(@Valid @RequestBody ContactMessageCreateRequest request) {
        return service.createContactMessage(request);
    }

    @GetMapping
    public List<ContactMessage> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
