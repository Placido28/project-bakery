package com.placidotech.pasteleria.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.enums.OrderStatus;
import com.placidotech.pasteleria.mapper.OrderMapper;
import com.placidotech.pasteleria.model.Order;
import com.placidotech.pasteleria.model.OrderItem;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.OrderItemRepository;
import com.placidotech.pasteleria.repository.OrderRepository;
import com.placidotech.pasteleria.repository.ProductRepository;
import com.placidotech.pasteleria.repository.UserRepository;
import com.placidotech.pasteleria.request.CreateOrderRequest;
import com.placidotech.pasteleria.request.OrderItemRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toDTO(order);
    }
    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .items(new HashSet<>())
                .build();
        
        request.getItems().forEach(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(itemRequest.getQuantity())
                .unitPrice(itemRequest.getPrice())
                .build();
            
            item.setTotalPrice();
            order.getItems().add(item);
            order.setTotalAmount(order.getTotalAmount().add(item.getTotalPrice()));
        });

        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.COMPLETED || order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update a completed or cancelled order");
        }

        if (order.getOrderStatus() == OrderStatus.PENDING && newStatus == OrderStatus.PROCESSING) {
            order.setOrderStatus(newStatus);
        } else if (order.getOrderStatus() == OrderStatus.PROCESSING && newStatus == OrderStatus.COMPLETED) {
            order.setOrderStatus(newStatus);
        } else if (newStatus == OrderStatus.CANCELLED) {
            order.setOrderStatus(newStatus);
        } else {
            throw new IllegalArgumentException("Invalid status transition");
        }

        orderRepository.save(order);
    }

    @Override
    public void addItemToOrder(Long orderId, OrderItemRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(request.getPrice())
                .build();
        
        orderItem.setTotalPrice();;
        order.getItems().add(orderItem);
        order.setTotalAmount(order.getTotalAmount().add(orderItem.getTotalPrice()));

        orderRepository.save(order);
    }


    @Override
    public void removeItemFromOrder(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        OrderItem orderItem = orderItemRepository.findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException("Order item not found"));
            
        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new IllegalArgumentException("Item does not belong to order");
        }

        order.getItems().remove(orderItem);
        order.setTotalAmount(order.getTotalAmount().subtract(orderItem.getTotalPrice()));
        orderItemRepository.delete(orderItem);
    }
    
    @Override
    public BigDecimal getTotalAmount(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return order.getTotalAmount();
    }

    public List<OrderItem> getItemsByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderItemRepository.findByOrder(order);
    }

    public List<OrderItem> getItemsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return orderItemRepository.findByProduct(product);
    }
}
