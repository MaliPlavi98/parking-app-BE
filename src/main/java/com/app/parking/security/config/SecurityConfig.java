package com.app.parking.security.config;

import com.app.parking.security.service.CustomAuthenticationProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // your custom provider
    @Bean
    public AuthenticationManager authenticationManager(CustomAuthenticationProvider customAuthenticationProvider) {
        return new ProviderManager(customAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http, AuthenticationManager authManager) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable).headers(headers -> headers
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authenticationManager(authManager)
            .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**")
                                               .permitAll().requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
