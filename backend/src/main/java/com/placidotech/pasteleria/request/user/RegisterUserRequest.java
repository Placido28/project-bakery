package com.placidotech.pasteleria.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password; // Contraseña proporcionada por el usuario

    @NotBlank
    private String firstName; 

    @NotBlank
    private String lastName;
    
}
