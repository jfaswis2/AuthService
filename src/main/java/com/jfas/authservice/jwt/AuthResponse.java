package com.jfas.authservice.jwt;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token
) {
}
