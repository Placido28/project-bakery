package com.placidotech.pasteleria.request;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
