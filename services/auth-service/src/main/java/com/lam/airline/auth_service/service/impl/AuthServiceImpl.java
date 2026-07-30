package com.lam.airline.auth_service.service.impl;

import com.lam.airline.auth_service.dto.LoginRequest;
import com.lam.airline.auth_service.dto.LoginResponse;
import com.lam.airline.auth_service.dto.RegisterRequest;
import com.lam.airline.auth_service.entity.User;
import com.lam.airline.auth_service.service.AuthService;
import com.lam.airline.auth_service.repository.UserRepository;
import com.lam.airline.auth_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
       User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid email or password ");
        }
        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);

    }
}
