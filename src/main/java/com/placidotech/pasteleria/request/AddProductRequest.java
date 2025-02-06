package com.placidotech.pasteleria.request;

import java.math.BigDecimal;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */
@Data
public class AddProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl; // URL de la imagen en Firebase
    private Long categoryId; // Id de la categoria
}
