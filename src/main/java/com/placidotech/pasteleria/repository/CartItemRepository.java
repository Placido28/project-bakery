package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.CartItem;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface CartItemRepository extends JpaRepository<Long, CartItem>{
    void deleteAllByCartId(Long id);
}
