package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Order;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
}
