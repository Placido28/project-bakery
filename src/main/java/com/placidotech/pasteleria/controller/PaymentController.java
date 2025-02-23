package com.placidotech.pasteleria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.request.MercadoPagoWebhookRequest;
import com.placidotech.pasteleria.request.PaymentRequest;
import com.placidotech.pasteleria.response.PaymentResponse;
import com.placidotech.pasteleria.service.payment.PaymentService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable String transactionId) {
        PaymentResponse response = paymentService.getPaymentStatus(transactionId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable String transactionId) {
        try {
            paymentService.updatePaymentStatus(transactionId);
            return ResponseEntity.ok("Payment status updated successfully");
        } catch (Exception e) {
            logger.error("Error updating payment status for transactionId={}", transactionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update payment status");
        }
    }

    // Recibe actualizaciones de pago desde MercadoPago y actualiza nuestra base de datos.
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody MercadoPagoWebhookRequest webhookRequest) {
        paymentService.handleWebhook(webhookRequest);
        return ResponseEntity.ok().build();
    }

    // Reintentar pago fallido
    @PostMapping("/retry/{transactionId}")
    public ResponseEntity<Void> retryFailedPayment(@PathVariable String transactionId) {
        paymentService.retryFailedPayment(transactionId);
        return ResponseEntity.ok().build();
    }

    // Cancelar pago
    @PostMapping("/cancel/{transactionId}")
    public ResponseEntity<Void> cancelPayment(@PathVariable String transactionId) {
        paymentService.cancelPayment(transactionId);
        return ResponseEntity.ok().build();
    }
}
