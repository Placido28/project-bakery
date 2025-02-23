package com.placidotech.pasteleria.request;

import java.util.Set;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long userId;
    private Long shippingAddressId; 
    private Set<CreateOrderItemRequest> items;
}
