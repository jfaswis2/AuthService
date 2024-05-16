package com.jfas.authservice.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignInRequest(
        @Email(message = "Email no válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=?])(?=.*[0-9a-zA-Z]).{9,}$",
                message = "Contraseña no válida")
        String password
) {
}
