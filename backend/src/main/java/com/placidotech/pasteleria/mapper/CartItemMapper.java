package com.placidotech.pasteleria.mapper;

import org.springframework.stereotype.Component;

import com.placidotech.pasteleria.dto.CartItemDTO;
import com.placidotech.pasteleria.model.CartItem;

/**
 *
 * @author CristopherPlacidoOca
 */
@Component
public class CartItemMapper {

    public CartItemDTO toDTO(CartItem cartItem){
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setUnitPrice(cartItem.getUnitPrice());
        dto.setTotalPrice(cartItem.getTotalPrice());
        dto.setProductId(cartItem.getProduct().getId());
        return dto;
    }

    public CartItem toEntity(CartItemDTO dto){
        CartItem cartItem = new CartItem();
        cartItem.setId(dto.getId());
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setUnitPrice(dto.getUnitPrice());
        // El producto y el carrito se asignan en el servicio
        return cartItem;
    }
}
