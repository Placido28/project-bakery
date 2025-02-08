package com.placidotech.pasteleria.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
public class CartItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Long productId;
}
