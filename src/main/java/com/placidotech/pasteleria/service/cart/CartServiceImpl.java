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
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.CartItemRepository;
import com.placidotech.pasteleria.repository.CartRepository;
import com.placidotech.pasteleria.repository.ProductRepository;
import com.placidotech.pasteleria.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO addItemToCart(Long cartId, CartItemRequest request, Long userId) {
        // Buscar el carrito por ID, o crear uno nuevo si no existe
        Cart cart = cartRepository.findById(cartId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setTotalAmount(BigDecimal.ZERO);

                // Si no hay usuario logueado, el carrito será anónimo (sin 'user' asociado)
                if (userId != null) {
                    User user = new User();
                    user.setId(userId);
                    newCart.setUser(user); // Asocia al usuario si está logueado
                }
                return cartRepository.save(newCart);
            });

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setQuantity(request.getQuantity());
        cartItemDTO.setUnitPrice(product.getPrice());
        cartItemDTO.setProductId(product.getId());

        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem.setCart(cart);
        cartItem.setProduct(product);

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

    @Override
    public void associateCartWithUser(Long cartId, Long userId){
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        cart.setUser(user);
        cartRepository.save(cart);
    }

}
