package com.placidotech.pasteleria.request;

import java.util.Set;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
public class CreateOrderRequest {
    private Long userId;
    private Set<CreateOrderItemRequest> items;
}
