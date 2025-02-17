package com.placidotech.pasteleria.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoogleLoginRequest {
    @NotBlank
    private String googleId;
    
    @NotBlank
    private String idToken;
}
