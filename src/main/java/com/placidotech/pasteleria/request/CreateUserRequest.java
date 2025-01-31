package com.placidotech.pasteleria.request;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
