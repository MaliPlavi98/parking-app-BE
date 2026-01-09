package com.app.parking.security.config;

import com.app.parking.security.jwt.JwtAuthFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // your custom provider
    //    @Bean
    //    public AuthenticationManager authenticationManager(CustomAuthenticationProvider customAuthenticationProvider) {
    //        return new ProviderManager(customAuthenticationProvider);
    //    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            //            .authenticationManager(authManager)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**").permitAll()

                    // 🔓 PUBLIC AUTH endpoints
                    .requestMatchers("/auth/login", "/auth/register", "/auth/logout").permitAll()

                    // 🔒 AUTHENTICATED endpoint
                    .requestMatchers("/auth/me").authenticated()

                    .requestMatchers(HttpMethod.POST, "/api/reservation/check").permitAll()
                    .requestMatchers("/error").permitAll()

                    .requestMatchers("/api/contact/**").hasRole("ADMIN")

                    .requestMatchers(HttpMethod.GET, "/api/setting/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/setting/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/setting/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/setting/**").hasRole("ADMIN")

                    .requestMatchers("/api/reservation/**").hasRole("ADMIN")

                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
