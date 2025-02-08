package com.placidotech.pasteleria.service.order;

import java.math.BigDecimal;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.enums.OrderStatus;
import com.placidotech.pasteleria.request.OrderItemRequest;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface IOrderService {
    OrderDTO getOrderById(Long id);
    OrderDTO createOrder(OrderDTO orderDTO);
    void updateOrderStatus(Long orderId, OrderStatus status);
    void addItemToOrder(Long orderId, OrderItemRequest request);
    void removeItemFromOrder(Long orderId, Long itemId);
    BigDecimal getTotalAmount(Long orderId);
}
