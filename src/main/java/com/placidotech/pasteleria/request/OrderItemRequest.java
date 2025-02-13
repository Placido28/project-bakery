package com.placidotech.pasteleria.request;

import java.math.BigDecimal;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
