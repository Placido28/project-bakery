package com.placidotech.pasteleria.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetPasswordRequest {
    @NotBlank
    private String activationToken;

    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
