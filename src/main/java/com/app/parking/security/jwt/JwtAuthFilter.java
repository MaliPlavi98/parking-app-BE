package com.app.parking.security.jwt;

import com.app.parking.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        // 🔍 1. Extract JWT token from cookies (not from Authorization header)
        String token = extractTokenFromCookie(request);

        if (token != null) {  // If a token exists in cookies
            // 🧩 2. Extract the username from the JWT
            String username = jwtUtil.extractUsername(token);

            // 🛡 3. Check:
            // - username is valid
            // - no authentication is yet set for this request
            // - the token is not expired, and signature is valid
            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtUtil.isValid(token)) {

                // 👤 4. Load user details from the database (or another source)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 🔐 5. Create an authentication object with user's authorities
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,     // principal (user)
                                null,            // no password needed here
                                userDetails.getAuthorities()   // roles/permissions
                        );

                // 🌍 6. Attach details like IP address, session ID, user agent, etc.
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ✅ 7. Mark user as authenticated for this request
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // ▶ 8. Continue the filter chain (Spring will proceed with the request)
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts JWT token from cookies.
     * We look for a cookie named "token".
     */
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        // Loop through all cookies in the request
        for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {

            // If there is a cookie named "token"
            if ("token".equals(cookie.getName())) {

                // return its value (the JWT)
                return cookie.getValue();
            }
        }

        // No token cookie found
        return null;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.startsWith("/auth")
                || uri.startsWith("/h2-console")
                || uri.startsWith("/api/reservation/check");
    }


}