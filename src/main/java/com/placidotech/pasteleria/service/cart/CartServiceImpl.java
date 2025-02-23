package com.placidotech.pasteleria.service.cart;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public CartDTO addItemToCart(Long cartId, CartItemRequest request, Long userId) {
        // Buscar el carrito por ID, o crear uno nuevo si no existe
        Cart cart = cartRepository.findById(cartId)
            .orElseGet(() -> createCart(userId));

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        CartItem cartItem = cart.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(product.getId()))
            .findFirst()
            .orElseGet(() -> {
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(0);
                newItem.setUnitPrice(product.getPrice());
                return cartItemRepository.save(newItem);
            });
        
        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

        cart.setTotalAmount(cart.getTotalAmount().add(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));

        cartRepository.save(cart);

        // Convertir la lista de CartItem a CartItemDTO
        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
            .map(cartItemMapper::toDTO)
            .collect(Collectors.toList());

        // Convertir Cart a CartDTO
        CartDTO cartDTO = cartMapper.toDTO(cart);
        cartDTO.setItems(new HashSet<>(cartItemDTOs));

        return cartDTO;
    }

    // Crear carrito nuevo (usuarios autenticados e invitados)
    private Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);

        if (userId != null) {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            cart.setUser(user);
        }

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
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
    @Transactional
    public void mergeCartWithUser(Long guestCartId, Long userId) {
        Cart guestCart = cartRepository.findById(guestCartId)
            .orElseThrow(() -> new RuntimeException("Guest cart not found"));
    
        Cart userCart = cartRepository.findByUserId(userId)
            .orElseGet(() -> createCart(userId));
    
        // Fusionar los productos del carrito de invitado al carrito del usuario
        for (CartItem guestItem : guestCart.getItems()) {
            CartItem existingItem = userCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(guestItem.getProduct().getId()))
                .findFirst()
                .orElse(null);
    
            if (existingItem != null) {
                // Si el producto ya está en el carrito del usuario, se suma la cantidad
                existingItem.setQuantity(existingItem.getQuantity() + guestItem.getQuantity());
            } else {
                // Si el producto no está en el carrito del usuario, se reasigna
                guestItem.setCart(userCart);
                userCart.getItems().add(guestItem);
            }
        }
    
        // Guardamos el carrito actualizado y eliminamos el carrito de invitado
        cartRepository.save(userCart);
        cartRepository.delete(guestCart);
    }

    @Override
    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();  // Elimina todos los productos del carrito
        cart.setTotalAmount(BigDecimal.ZERO); // Reinicia el total

        cartRepository.save(cart);
    }
}
