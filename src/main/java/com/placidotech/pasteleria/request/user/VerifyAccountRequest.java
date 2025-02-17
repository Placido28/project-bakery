package com.placidotech.pasteleria.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
public class VerifyAccountRequest {
    @NotBlank
    private String activationToken;
}
