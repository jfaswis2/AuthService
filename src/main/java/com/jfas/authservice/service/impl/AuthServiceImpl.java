package com.jfas.authservice.service.impl;

import com.jfas.authservice.exception.EmailAlreadyExistsException;
import com.jfas.authservice.exception.EmailNotFoundException;
import com.jfas.authservice.jwt.AuthResponse;
import com.jfas.authservice.jwt.JwtService;
import com.jfas.authservice.jwt.SignInRequest;
import com.jfas.authservice.jwt.SignUpRequest;
import com.jfas.authservice.model.Role;
import com.jfas.authservice.model.User;
import com.jfas.authservice.repository.UserRepository;
import com.jfas.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //REGISTER
    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new EmailAlreadyExistsException("The email " + signUpRequest.email() + " is already in use");
        }

        User user = User.builder()
                .name(signUpRequest.name())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .email(signUpRequest.email())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    //LOGIN
    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        UserDetails user = userRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new EmailNotFoundException("The email " + signInRequest.email() + " was not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password()));

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }
}
