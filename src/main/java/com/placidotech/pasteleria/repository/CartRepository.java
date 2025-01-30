package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Cart;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface CartRepository extends JpaRepository<Long, Cart>{
    Cart findByUserId(Long userId);
}
