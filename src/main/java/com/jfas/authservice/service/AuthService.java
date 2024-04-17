package com.jfas.authservice.service;

import com.jfas.authservice.jwt.AuthResponse;
import com.jfas.authservice.jwt.SignUpRequest;

public interface AuthService {

    AuthResponse signUp(SignUpRequest signUpRequest);
}
