package com.placidotech.pasteleria.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyAccountRequest {
    @NotBlank
    private String activationToken;
}
