package com.placidotech.pasteleria.service.category;

import java.util.List;

import com.placidotech.pasteleria.dto.CategoryDTO;
import com.placidotech.pasteleria.model.Category;
import com.placidotech.pasteleria.request.AddCategoryRequest;
import com.placidotech.pasteleria.request.CategoryUpdateRequest;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface ICategoryService {

    // Agregar una nueva categoría
    Category addCategory(AddCategoryRequest category);

    // Obtener una categoría por ID
    Category getCategoryById(Long id);

    // Eliminar una categoría por ID
    void deleteCategoryById(Long id);

    // Actualizar una categoría existente
    Category updateCategory(CategoryUpdateRequest category, Long categoryId);

    // Obtener todas las categorías
    List<Category> getAllCategories();

    // Buscar categorías por nombre
    List<Category> searchCategoriesByName(String name);

    // Convertir una lista de categorías a una lista de DTOs
    List<CategoryDTO> getConvertedCategories(List<Category> categories);

    // Convertir una categoría a DTO
    CategoryDTO convertToDto(Category category);
}
