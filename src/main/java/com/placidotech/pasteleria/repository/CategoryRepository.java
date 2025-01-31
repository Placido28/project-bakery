package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Category;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Category findByName(String name);

    boolean existsByName(String name);
}
