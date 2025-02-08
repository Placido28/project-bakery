package com.placidotech.pasteleria.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.CartDTO;
import com.placidotech.pasteleria.dto.CartItemDTO;
import com.placidotech.pasteleria.mapper.CartItemMapper;
import com.placidotech.pasteleria.mapper.CartMapper;
import com.placidotech.pasteleria.model.Cart;
import com.placidotech.pasteleria.model.CartItem;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.repository.CartItemRepository;
import com.placidotech.pasteleria.repository.CartRepository;
import com.placidotech.pasteleria.repository.ProductRepository;
import com.placidotech.pasteleria.request.CartItemRequest;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author CristopherPlacidoOca
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService{

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long cartId, CartItemRequest request) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        CartItem cartItem = cartItemMapper.toEntity(new CartItemDTO());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        cart.addItem(cartItem);
        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalAmount(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getTotalAmount();
    }


}
