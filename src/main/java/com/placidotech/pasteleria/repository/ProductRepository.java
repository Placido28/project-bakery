package com.placidotech.pasteleria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Product;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategoryName(String category);

    List<Product> findByName(String name);

    long countByName(String name);
}
