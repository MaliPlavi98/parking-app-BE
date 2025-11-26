package com.app.parking.security.controller;

import com.app.parking.security.dto.LoginRequest;
import com.app.parking.security.dto.LoginResponse;
import com.app.parking.security.dto.RegisterRequest;
import com.app.parking.security.jwt.JwtUtil;
import com.app.parking.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = jwtUtil.generateToken(request.username());
        return new LoginResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Create user + save to DB
        userService.register(request.username(), request.password());
        return ResponseEntity.ok("Registered");
    }
}