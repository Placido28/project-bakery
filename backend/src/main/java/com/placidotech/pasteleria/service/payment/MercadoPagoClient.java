package com.placidotech.pasteleria.service.payment;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.placidotech.pasteleria.request.PaymentRequest;
import com.placidotech.pasteleria.response.MercadoPagoPaymentResponse;

@Service
public class MercadoPagoClient {
    private static final String MERCADO_PAGO_URL = "https://api.mercadopago.com/v1/payments/";


    @Value("${mercadopago.access-token}")
    private String accessToken;

    private static final Logger log = LoggerFactory.getLogger(MercadoPagoClient.class);

    public MercadoPagoPaymentResponse createPayment(PaymentRequest paymentRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        // Cuerpo de la solicituf
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("transaction_amount", paymentRequest.getAmount());
        requestBody.put("description", "Pago de pedido #" + paymentRequest.getOrderId());
        requestBody.put("payment_method_id", paymentRequest.getPaymentMethodId());
        requestBody.put("payer", Map.of("email", paymentRequest.getPayerEmail()));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<MercadoPagoPaymentResponse> response = restTemplate.postForEntity(
            MERCADO_PAGO_URL, request,MercadoPagoPaymentResponse.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Failed to create payment in MercadoPago");
        }

        log.info("Created payment in MercadoPago: {}", response.getBody());
        return response.getBody();
    }
    
    public MercadoPagoPaymentResponse getPaymentStatus(String transactionId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MercadoPagoPaymentResponse> response = restTemplate.exchange(
            MERCADO_PAGO_URL + transactionId, HttpMethod.GET, entity, MercadoPagoPaymentResponse.class
        );
        
        log.info("Getting payment status from MercadoPago for transactionId: {}", response.getBody());
        return response.getBody();
    }

}
