package com.lam.airline.auth_service.service;

import com.lam.airline.auth_service.dto.LoginRequest;
import com.lam.airline.auth_service.dto.LoginResponse;
import com.lam.airline.auth_service.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
