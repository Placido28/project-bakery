package com.placidotech.pasteleria.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */

@Data
public class CreateUserRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String role; //"ROLE_USER" o "ROLE_ADMIN"
}
