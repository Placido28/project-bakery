package com.placidotech.pasteleria.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.enums.OrderStatus;
import com.placidotech.pasteleria.model.OrderItem;
import com.placidotech.pasteleria.request.CreateOrderRequest;
import com.placidotech.pasteleria.request.OrderItemRequest;
import com.placidotech.pasteleria.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        OrderDTO order = orderService.createOrder(request);
        return ResponseEntity.status(CREATED).body(order);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<Void> addItemToOrder(@PathVariable Long orderId, @RequestBody OrderItemRequest request){
        orderService.addItemToOrder(orderId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId){
        orderService.removeItemFromOrder(orderId, itemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}/total")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getTotalAmount(orderId));
    }
    
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status){
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getItemsByOrder(orderId));
    }

    @GetMapping("/product/{productId}/items")
    public ResponseEntity<List<OrderItem>> getItemsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(orderService.getItemsByProduct(productId));
    }
    
}
