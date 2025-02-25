package com.placidotech.pasteleria.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.model.FailedWebhook;
import com.placidotech.pasteleria.repository.FailedWebhookRepository;
import com.placidotech.pasteleria.request.MercadoPagoWebhookRequest;
import com.placidotech.pasteleria.service.payment.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/webhook/mercadopago")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoWebhookController.class);

    private final PaymentService paymentService;
    private final FailedWebhookRepository failedWebhookRepository;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody MercadoPagoWebhookRequest webhookRequest) {
        logger.info("Received webhook from MercadoPago: {}", webhookRequest);

        try {
            String transactioId = webhookRequest.getData_id();
            String eventType = webhookRequest.getType();

            if (transactioId == null || transactioId.isEmpty()) {
                logger.warn("Webhook received without a valid transactionId");
                return ResponseEntity.badRequest().body("Invalid webhook: Missing transaction ID");
                
            }

            if (!"payment".equals(eventType)) {
                logger.info("Ignoring webhook event of type: {}", eventType);
                return ResponseEntity.badRequest().body("Unhandled event type");
                
            }

            // Actualizar el estado del pago
            paymentService.updatePaymentStatus(transactioId);
            return ResponseEntity.ok("Payment status updated");

        } catch (Exception e) {
            logger.error("Error processing MercadoPago webhook", e);

            // Si hay un transactionId válido, guardamos el webhook para reintento
            if (webhookRequest.getData_id() != null) {
                FailedWebhook failedWebhook = new FailedWebhook();
                failedWebhook.setTransactionId(webhookRequest.getData_id());
                failedWebhook.setPayload(webhookRequest.toString());
                failedWebhookRepository.save(failedWebhook);
                
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
