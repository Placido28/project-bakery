package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placidotech.pasteleria.model.Cart;

/**
 *
 * @author CristopherPlacidoOca
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
}
