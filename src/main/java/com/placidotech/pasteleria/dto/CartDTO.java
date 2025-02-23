package com.placidotech.pasteleria.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDTO> items;
}
