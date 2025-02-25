package com.placidotech.pasteleria.request.user;

import lombok.Data;

@Data
public class TokenRequest {
    private String refreshToken;
}
