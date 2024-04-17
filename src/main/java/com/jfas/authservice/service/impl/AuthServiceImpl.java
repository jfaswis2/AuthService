package com.jfas.authservice.service.impl;

import com.jfas.authservice.exception.EmailAlreadyExistsException;
import com.jfas.authservice.jwt.AuthResponse;
import com.jfas.authservice.jwt.SignUpRequest;
import com.jfas.authservice.model.User;
import com.jfas.authservice.repository.UserRepository;
import com.jfas.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new EmailAlreadyExistsException("The email " + signUpRequest.email() + " is already in use");
        }

        User user = User.builder()
                .name(signUpRequest.name())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .email(signUpRequest.email())
                .build();

        userRepository.save(user);
        return new AuthResponse("TOKEN");
    }
}
