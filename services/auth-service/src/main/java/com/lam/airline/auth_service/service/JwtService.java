package com.lam.airline.auth_service.service;

import com.lam.airline.auth_service.entity.User;

public interface JwtService {
    String generateToken(String email);

    String extractUsername(String token);

    boolean isTokenValid(String token, User user);
}
