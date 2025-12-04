package com.app.parking.security.dto;

import com.app.parking.entity.User;

public record LoginResponse(String token, UserDto userDto) {
}