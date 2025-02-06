package com.placidotech.pasteleria.service.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.CategoryDTO;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.mapper.CategoryMapper;
import com.placidotech.pasteleria.model.Category;
import com.placidotech.pasteleria.repository.CategoryRepository;
import com.placidotech.pasteleria.request.AddCategoryRequest;
import com.placidotech.pasteleria.request.CategoryUpdateRequest;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author CristopherPlacidoOca
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(AddCategoryRequest category) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    @Override
    public Category updateCategory(CategoryUpdateRequest category, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    @Override
    public List<CategoryDTO> getConvertedCategories(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO convertToDto(Category category) {
        return CategoryMapper.toDTO(category);
    }


}
