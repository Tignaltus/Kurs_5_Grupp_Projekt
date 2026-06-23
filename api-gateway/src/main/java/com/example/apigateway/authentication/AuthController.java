package com.example.apigateway.authentication;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        if (request.username().equals("admin") && request.password().equals("password")) {
            String token = jwtService.generateToken(request.username(), List.of("ADMIN", "USER"));
            return new LoginResponse(token);
        }

        if (request.username().equals("user") && request.password().equals("password")) {
            String token = jwtService.generateToken(request.username(), List.of("USER"));
            return new LoginResponse(token);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
}