package com.placidotech.pasteleria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.FailedWebhook;

public interface FailedWebhookRepository extends JpaRepository<FailedWebhook, Long> {
    // Busca todos los webhooks que aún no han sido procesados
    List<FailedWebhook> findByProcessedFalse();
}
