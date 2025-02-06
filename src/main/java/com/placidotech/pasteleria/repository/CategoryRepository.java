package com.placidotech.pasteleria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Category;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    // Buscar categoría por nombre
    Optional<Category> findByName(String name);

    // Obtener todas las categorías
    List<Category> findAll();

    // Obtener productos asociados a una categoría
    List<Category> findProductsByCategoryId(Long categoryId);

    List<Category> findByNameContaining(String name);
}
