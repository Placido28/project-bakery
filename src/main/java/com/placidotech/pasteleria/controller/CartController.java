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



/**
 *
 * @author CristopherPlacidoOca
 */
@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private  ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }
    
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemRequest request, @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(cartService.addItemToCart(cartId, request, userId));
    }
    
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        cartService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getTotalAmount(cartId));
    }
    
}
