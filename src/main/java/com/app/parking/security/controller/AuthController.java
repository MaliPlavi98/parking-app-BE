package com.app.parking.security.controller;

import com.app.parking.entity.User;
import com.app.parking.security.dto.LoginRequest;
import com.app.parking.security.dto.LoginResponse;
import com.app.parking.security.dto.RegisterRequest;
import com.app.parking.security.dto.UserDto;
import com.app.parking.security.jwt.JwtUtil;
import com.app.parking.security.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // Load user from DB
        User user = userService.findByUsername(request.username())
                               .orElseThrow(() -> new RuntimeException("User not found"));

        // Map to DTO
        UserDto userDto = new UserDto(
                user.getUsername(),
                user.getRole().name()
        );

        String token = jwtUtil.generateToken(request.username());

        ResponseCookie responseCookie = ResponseCookie.from("token", token).httpOnly(true)
                                                      .secure(true)
                                                      .sameSite("None")
                                                      .path("/")
                                                      .maxAge(7 * 24 * 60 * 60)
                                                      .build();

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                             .body(new LoginResponse(token, userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Create user + save to DB
        userService.register(request.username(), request.password());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("token", "")
                                                    .httpOnly(true)
                                                    .secure(true)       // true in production
                                                    .sameSite("None")    // required for cross-origin
                                                    .path("/")
                                                    .maxAge(0)           // <-- delete cookie
                                                    .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                             .body("Logged out");
    }

}