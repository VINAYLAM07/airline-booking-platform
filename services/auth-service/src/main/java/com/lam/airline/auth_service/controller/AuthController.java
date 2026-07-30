package com.lam.airline.auth_service.controller;

import com.lam.airline.auth_service.dto.LoginRequest;
import com.lam.airline.auth_service.dto.LoginResponse;
import com.lam.airline.auth_service.dto.RegisterRequest;
import com.lam.airline.auth_service.service.AuthService;
import com.lam.airline.auth_service.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;
    private  final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/test")
    public String testToken(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        return jwtService.extractUsername(token);

    }
}
