package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placidotech.pasteleria.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}
