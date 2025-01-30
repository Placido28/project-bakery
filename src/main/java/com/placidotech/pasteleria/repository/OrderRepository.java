package com.placidotech.pasteleria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Order;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUserId(Long userId);
}
