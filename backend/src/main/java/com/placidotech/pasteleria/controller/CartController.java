package com.placidotech.pasteleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.CartDTO;
import com.placidotech.pasteleria.request.CartItemRequest;
import com.placidotech.pasteleria.service.cart.ICartService;


import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private  ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }
    
    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartDTO> addItemToCart(
        @PathVariable Long cartId, 
        @RequestBody CartItemRequest request, 
        @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(cartService.addItemToCart(cartId, request, userId));
    }
    
    @DeleteMapping("/{cartId}/remove/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(
        @PathVariable Long cartId, 
        @PathVariable Long itemId){
        cartService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getTotalAmount(cartId));
    }

    // Fusionar carrito de invitado con el del usuario autenticado
    @PostMapping("/merge")
    public ResponseEntity<Void> mergeCartWithUser(
            @RequestParam Long guestCartId,
            @RequestParam Long userId) {

        cartService.mergeCartWithUser(guestCartId, userId);
        return ResponseEntity.ok().build();
    }
    
}
