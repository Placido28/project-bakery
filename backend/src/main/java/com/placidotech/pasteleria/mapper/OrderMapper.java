package com.placidotech.pasteleria.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.dto.OrderItemDTO;
import com.placidotech.pasteleria.model.Order;
import com.placidotech.pasteleria.model.OrderItem;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.model.User;

/**
 *
 * @author CristopherPlacidoOca
 */

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .userId(order.getUser().getId())
                .items(toOrderItemDTOSet(order.getItems()))
                .build();
    }

    public Order toEntity(OrderDTO orderDTO, User user) {
        if (orderDTO == null) {
            return null;
        }
        return Order.builder()
                .id(orderDTO.getId())
                .orderDate(orderDTO.getOrderDate())
                .totalAmount(orderDTO.getTotalAmount())
                .orderStatus(orderDTO.getOrderStatus())
                .user(user)
                .items(toOrderItemSet(orderDTO.getItems()))
                .build();
    }

    public OrderItemDTO toDTO(OrderItem item) {
        if (item == null) {
            return null;
        }
        return OrderItemDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .price(item.getUnitPrice())
                .build();
    }

    public OrderItem toEntity(OrderItemDTO itemDTO, Product product, Order order) {
        if (itemDTO == null) {
            return null;
        }
        OrderItem orderItem = OrderItem.builder()
                .id(itemDTO.getId())
                .product(product)
                .order(order)
                .quantity(itemDTO.getQuantity())
                .unitPrice(itemDTO.getPrice())
                .build();
        orderItem.setTotalPrice();
        return orderItem;
    }

    public Set<OrderItemDTO> toOrderItemDTOSet(Set<OrderItem> items) {
        if (items == null) {
            return Collections.emptySet();
        }
        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<OrderItem> toOrderItemSet(Set<OrderItemDTO> itemDTOs) {
        if (itemDTOs == null) {
            return Collections.emptySet();
        }
        return itemDTOs.stream()
                .map(dto -> toEntity(dto, null, null)) // Producto y Order deben ser asignados después
                .collect(Collectors.toSet());
    }
}
