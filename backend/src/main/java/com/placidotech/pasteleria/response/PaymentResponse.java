package com.placidotech.pasteleria.response;

import com.placidotech.pasteleria.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private String paymentUrl;
    private PaymentStatus status;
}
