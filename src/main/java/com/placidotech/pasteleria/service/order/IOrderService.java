package com.placidotech.pasteleria.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.enums.OrderStatus;
import com.placidotech.pasteleria.model.OrderItem;
import com.placidotech.pasteleria.request.CreateOrderRequest;
import com.placidotech.pasteleria.request.OrderItemRequest;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface IOrderService {
    OrderDTO getOrderById(Long id);
    OrderDTO createOrder(CreateOrderRequest request);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    void addItemToOrder(Long orderId, OrderItemRequest request);
    void removeItemFromOrder(Long orderId, Long itemId);
    BigDecimal getTotalAmount(Long orderId);
    List<OrderItem> getItemsByOrder(Long orderId);
    List<OrderItem> getItemsByProduct(Long productId);
}
