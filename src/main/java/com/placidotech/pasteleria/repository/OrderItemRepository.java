package com.placidotech.pasteleria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placidotech.pasteleria.model.Order;
import com.placidotech.pasteleria.model.OrderItem;
import com.placidotech.pasteleria.model.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByProduct(Product product);

}
