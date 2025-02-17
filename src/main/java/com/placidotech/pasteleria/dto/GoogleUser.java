package com.placidotech.pasteleria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUser {
    private String firstName;
    private String lastName;
    private String email;
    private String googleId;
}
