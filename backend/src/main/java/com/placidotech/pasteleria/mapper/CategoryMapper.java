package com.placidotech.pasteleria.mapper;

import com.placidotech.pasteleria.dto.CategoryDTO;
import com.placidotech.pasteleria.model.Category;

/**
 *
 * @author CristopherPlacidoOca
 */
public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static Category toEntity(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
