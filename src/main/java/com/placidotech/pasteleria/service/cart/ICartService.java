package com.placidotech.pasteleria.service.cart;

import java.math.BigDecimal;

import com.placidotech.pasteleria.dto.CartDTO;
import com.placidotech.pasteleria.request.CartItemRequest;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface ICartService {

    CartDTO getCartById(Long id);
    CartDTO addItemToCart(Long cartId, CartItemRequest request, Long userId);
    void removeItemFromCart(Long cartId, Long itemId);
    BigDecimal getTotalAmount(Long cartId);
    void associateCartWithUser(Long cartId, Long userId);
}
