package com.placidotech.pasteleria.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.placidotech.pasteleria.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
@Builder
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Long userId;
    private Set<OrderItemDTO> items;
}
