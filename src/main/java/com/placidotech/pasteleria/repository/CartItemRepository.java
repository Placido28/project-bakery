package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placidotech.pasteleria.model.CartItem;

/**
 *
 * @author CristopherPlacidoOca
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
}
