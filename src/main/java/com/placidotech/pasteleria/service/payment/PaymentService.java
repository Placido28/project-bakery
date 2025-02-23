package com.placidotech.pasteleria.service.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placidotech.pasteleria.enums.OrderStatus;
import com.placidotech.pasteleria.enums.PaymentStatus;
import com.placidotech.pasteleria.model.Order;
import com.placidotech.pasteleria.model.Payment;
import com.placidotech.pasteleria.repository.OrderRepository;
import com.placidotech.pasteleria.repository.PaymentRepository;
import com.placidotech.pasteleria.request.MercadoPagoWebhookRequest;
import com.placidotech.pasteleria.request.PaymentRequest;
import com.placidotech.pasteleria.response.MercadoPagoPaymentResponse;
import com.placidotech.pasteleria.response.PaymentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MercadoPagoClient mercadoPagoClient;
    private final OrderRepository   orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        // Buscar la orden en la base de datos
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Llamamos a MercadoPago para genera el pago
        MercadoPagoPaymentResponse mercadoPagoResponse = mercadoPagoClient.createPayment(paymentRequest);

        // Guardamos el pago en nuestra BD
        Payment payment = new Payment();
        payment.setTransactionId(mercadoPagoResponse.getTransactionId());
        payment.setPaymentGateway("MERCADOPAGO");
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(BigDecimal.valueOf(paymentRequest.getAmount()));
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrder(order);

        paymentRepository.save(payment);

        return new PaymentResponse(
            payment.getTransactionId(),
            mercadoPagoResponse.getPaymentUrl(),
            payment.getStatus()
        );
    }

    public PaymentResponse getPaymentStatus(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return new PaymentResponse(
            payment.getTransactionId(),
            null,
            payment.getStatus()
        );
    }

    @Transactional
    public void handleWebhook(MercadoPagoWebhookRequest webhookRequest){
        String transactionId = webhookRequest.getData_id();

        if (transactionId == null || transactionId.isEmpty()) {
            throw new IllegalArgumentException("Transaction ID is missing in webhook request");
            
        }

        // Obtener el estado real del pago desde MercadoPago
        MercadoPagoPaymentResponse paymentResponse = mercadoPagoClient.getPaymentStatus(transactionId);

        if (paymentResponse == null || paymentResponse.getStatus() == null) {
            throw new RuntimeException("Could not retrieve payment status from MercadoPago");
            
        }

        String status = paymentResponse.getStatus();

        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found for transactionId: " + transactionId));

        payment.setStatus(PaymentStatus.valueOf(status));
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // Si el pago es exitoso, actualizamos el estado de la orden
        if (PaymentStatus.APPROVED.name().equals(status)) {
            Order order = payment.getOrder();
            if (order != null) {
                order.setOrderStatus(OrderStatus.PROCESSING);
                orderRepository.save(order);
            }else{
                logger.warn("Order not found for payment: TransactionId={}", transactionId);
            }
        }

        logger.info("Webhook processed successfully: TransactionId={}, Status={}", transactionId, status);
    }

    @Transactional
    public void updatePaymentStatus(String transactionId) {
        MercadoPagoPaymentResponse response = mercadoPagoClient.getPaymentStatus(transactionId);

        if (response == null) {
            throw new RuntimeException("Could not get payment status");
        }

        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setStatus(PaymentStatus.valueOf(response.getStatus()));
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // Si el pago es exitoso, actualizamos el estado de la orden
        if ("APPROVED".equals(response.getStatus())) {
            Order order = payment.getOrder();
            order.setOrderStatus(OrderStatus.PROCESSING);
            orderRepository.save(order);
        }

        logger.info("Updated payment: TransactionId={}, Status={}", transactionId, response.getStatus());
    }

    @Transactional
    public void retryFailedPayment(String transactionId){
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!"FAILED".equals(payment.getStatus())) {
            throw new IllegalArgumentException("Payment is not in FAILED status");
        }

        MercadoPagoPaymentResponse mercadoPagoResponse = mercadoPagoClient.createPayment(
            new PaymentRequest(payment.getOrder().getId(), payment.getAmount().doubleValue())
        );

        payment.setTransactionId(mercadoPagoResponse.getTransactionId());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        logger.info("Retried payment: NewTransactionId={}, Status=PENDING", mercadoPagoResponse.getTransactionId());
    }

    @Transactional
    public void cancelPayment(String transactionId){
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!"PENDING".equals(payment.getStatus())) {
            throw new IllegalArgumentException("Only PENDING payments can be canceled");
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        logger.info("Cancelled payment: TransactionId={}, Status=CANCELLED", transactionId);
    }

}
