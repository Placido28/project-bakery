package com.placidotech.pasteleria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Product;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    // Obtener productos no eliminados
    List<Product> findByRemovedFalse();

    // Buscar productos por nombre (excluyendo eliminados)
    List<Product> findByNameContainingAndRemovedFalse(String name, boolean removed);

    // Buscar productos por categoría (excluyendo eliminados)
    List<Product> findByCategoryIdAndRemovedFalse(Long categoryId, boolean removed);
}
