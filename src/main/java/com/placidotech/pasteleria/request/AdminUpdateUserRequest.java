package com.placidotech.pasteleria.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
