package com.placidotech.pasteleria.request;

import lombok.Data;

@Data
public class ActivationRequest {
    private String token;
    private String newPassword;
}
