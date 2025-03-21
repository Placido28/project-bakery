package com.placidotech.pasteleria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.placidotech.pasteleria.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.disable())
            .csrf(csrf ->
                csrf.ignoringRequestMatchers("/ws/**")
                .disable())
            .authorizeHttpRequests(authRequests ->
                authRequests
                        .requestMatchers("/ws/**").permitAll()
                    .requestMatchers("/api/auth/**", "/api/users/**", "/api/auth/google/**").permitAll()
                    .anyRequest().authenticated()
                    )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();    
    }
}
