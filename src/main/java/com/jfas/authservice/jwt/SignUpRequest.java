package com.jfas.authservice.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(

        @NotBlank(message = "El nombre no debe de ser nulo, vacio ni contener solo espacios en blanco")
        String name,
        @Email(message = "Email no válido", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=?])(?=.*[0-9a-zA-Z]).{9,}$",
                message = "La contraseña debe tener al menos 9 caracteres, " +
                        "debe incluir al menos una letra en mayúscula, " +
                        "al menos un carácter especial y " +
                        "no tener espacios en blanco.")
        String password
) {
}
