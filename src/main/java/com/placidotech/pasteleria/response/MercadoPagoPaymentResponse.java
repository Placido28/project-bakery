package com.placidotech.pasteleria.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MercadoPagoPaymentResponse {
    @JsonProperty("id")
    private String transactionId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("init_point")
    private String paymentUrl;
}
