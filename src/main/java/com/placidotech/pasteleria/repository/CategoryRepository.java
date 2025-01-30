package com.placidotech.pasteleria.repository;

import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByName(String name);

    boolean existsByName(String name);
}
