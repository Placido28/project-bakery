package com.placidotech.pasteleria.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placidotech.pasteleria.dto.CartDTO;
import com.placidotech.pasteleria.model.Cart;

/**
 *
 * @author CristopherPlacidoOca
 */
@Component
public class CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    public CartDTO toDTO(Cart cart){
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotalAmount(cart.getTotalAmount());
        dto.setItems(cart.getItems().stream()
            .map(cartItemMapper::toDTO)
            .collect(Collectors.toSet()));
        return dto;
    }

    public Cart toEntity(CartDTO dto){
        Cart cart = new Cart();
        cart.setId(dto.getId());
        cart.setTotalAmount(dto.getTotalAmount());
        // Los ítems se manejan en el servicio para evitar problemas de persistencia
        return cart;
    }
}
