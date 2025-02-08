package com.placidotech.pasteleria.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.enums.OrderStatus;
import com.placidotech.pasteleria.mapper.OrderMapper;
import com.placidotech.pasteleria.model.Order;
import com.placidotech.pasteleria.model.OrderItem;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.OrderRepository;
import com.placidotech.pasteleria.repository.ProductRepository;
import com.placidotech.pasteleria.repository.UserRepository;
import com.placidotech.pasteleria.request.CreateOrderRequest;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author CristopherPlacidoOca
 */

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .build();
        request.getItems().forEach(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(itemRequest.getQuantity())
                .price(itemRequest.getPrice())
                .build();
            
            order.getItems().add(item);
            order.setTotalAmount(order.getTotalAmount().add(itemRequest.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()))));
        });

        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }
    @Override
    public List<OrderDTO> getAllorders() {
        return orderRepository.findAll().stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDTO).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
