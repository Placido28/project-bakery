package com.placidotech.pasteleria.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "failed_webhooks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FailedWebhook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String payload; // Guardamos el JSON del webhook
    private Boolean processed = false; // Indica si el webhook fue procesado
    private LocalDateTime createdAt;
}
