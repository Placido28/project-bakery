package com.placidotech.pasteleria.mapper;

import com.placidotech.pasteleria.dto.ProductDTO;
import com.placidotech.pasteleria.model.Product;

/**
 *
 * @author CristopherPlacidoOca
 */
public class ProductMapper {

    public static ProductDTO toDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setImageUrl(product.getImagenUrl());
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        return productDTO;
    }

    public static Product toEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImagenUrl(productDTO.getImageUrl());
        // La categoria se asigna en el servicio usando el ID
        return product;
    }
}
