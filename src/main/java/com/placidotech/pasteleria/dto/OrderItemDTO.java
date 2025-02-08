package com.placidotech.pasteleria.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
@Builder
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
