package com.placidotech.pasteleria.service.payment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.model.FailedWebhook;
import com.placidotech.pasteleria.repository.FailedWebhookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebhookRetryService {

    private final FailedWebhookRepository failedWebhookRepository;
    private final PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(WebhookRetryService.class);

    @Scheduled(fixedDelay = 60000) // Ejecuta cada minuto
    public void retryFailedWebhooks() {
        List<FailedWebhook> failedWebhooks = failedWebhookRepository.findByProcessedFalse();

        for (FailedWebhook webhook : failedWebhooks) {
            try {
                paymentService.updatePaymentStatus(webhook.getTransactionId());
                webhook.setProcessed(true);
                failedWebhookRepository.save(webhook);
                logger.info("Webhook retried successfully: {}", webhook.getTransactionId());
            } catch (Exception e) {
                logger.error("Error retrying webhook: {}", e.getMessage());
            }
        }
    }
}
