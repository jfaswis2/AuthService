package com.jfas.authservice.jwt;

public record SignUpRequest(
        String name,
        String email,
        String password
) {
}
