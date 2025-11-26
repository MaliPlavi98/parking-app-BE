package com.app.parking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactMessageCreateRequest(

        @NotBlank
        @Size(max = 255)
        String title,

        @NotBlank
        @Size(max = 255)
        String name,

        @NotBlank
        @Size(max = 50)
        String phone,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 5, max = 5000)
        String message
) {
}
