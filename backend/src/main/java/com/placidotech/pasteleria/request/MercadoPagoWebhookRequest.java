package com.placidotech.pasteleria.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoWebhookRequest {
    private String id;             // ID del evento de webhook
    private String action;         // Acción del evento (e.g., "payment.updated")
    private String type;           // Tipo del evento ("payment", "chargeback", etc.)
    private String date_created;   // Fecha de creación del evento
    private Long user_id;          // ID del usuario de MercadoPago
    private Long api_version;      // Versión de la API
    private String live_mode;      // Si es entorno de producción o sandbox
    private String application_id; // ID de la aplicación en MercadoPago
    private String data_id;        // ID del pago, la orden, etc. (el más importante)
}
