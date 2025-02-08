package com.placidotech.pasteleria.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.exception.OrderNotFoundException;
import com.placidotech.pasteleria.request.CreateOrderRequest;
import com.placidotech.pasteleria.response.ApiResponse;
import com.placidotech.pasteleria.service.order.IOrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 *
 * @author CristopherPlacidoOca
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> createOrder(@RequestBody CreateOrderRequest request){
        return ResponseEntity.ok(new ApiResponse("Order created successfully", orderService.createOrder(request)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllorders();
        return ResponseEntity.ok(new ApiResponse("Orders retrieved succesfully", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", orderService.getOrderById(id)));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
}
