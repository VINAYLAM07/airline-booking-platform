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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.email());

        return new LoginResponse(token);

    }
}
