package com.placidotech.pasteleria.service.product;

import java.util.List;

import com.placidotech.pasteleria.dto.ProductDTO;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.request.AddProductRequest;
import com.placidotech.pasteleria.request.ProductUpdateRequest;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface IProductService {

    // Agregar un nuevo producto
    Product addProduct(AddProductRequest product);

    // Obtener un producto por ID
    Product getProductById(Long id);

    // Eliminar un producto por ID (soft delete)
    void deleteProductById(Long id);

    // Actualizar un producto existente
    Product updateProduct(ProductUpdateRequest product, Long productId);

    // Obtener todos los productos no eliminados
    List<Product> getAllProducts();

    // Buscar productos por nombre (excluyendo eliminados)
    List<Product> searchProductsByName(String name);

    // Obtener productos por categoría (excluyendo eliminados)
    List<Product> getProductsByCategory(Long categoryId);

    // Convertir una lista de productos a una lista de DTOs
    List<ProductDTO> getConvertedProducts(List<Product> products);

    // Convertir un producto a DTO
    ProductDTO convertToDto(Product product);
}
