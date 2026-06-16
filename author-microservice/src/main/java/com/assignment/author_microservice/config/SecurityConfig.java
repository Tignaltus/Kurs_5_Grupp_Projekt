package com.assignment.author_microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())          // Stänger av CSRF
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll()      // Tillåt ALLA requests
                    )
                    .formLogin(form -> form.disable())     // Ingen login‑sida
                    .httpBasic(basic -> basic.disable());  // Ingen Basic Auth

            return http.build();
        }
    }


