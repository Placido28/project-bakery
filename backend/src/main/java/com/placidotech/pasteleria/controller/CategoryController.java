package com.placidotech.pasteleria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.CategoryDTO;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.model.Category;
import com.placidotech.pasteleria.request.AddCategoryRequest;
import com.placidotech.pasteleria.request.CategoryUpdateRequest;
import com.placidotech.pasteleria.response.ApiResponse;
import com.placidotech.pasteleria.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody AddCategoryRequest request) {
        Category category = categoryService.addCategory(request);
        CategoryDTO categoryDTO = categoryService.convertToDto(category);
        return ResponseEntity.ok(new ApiResponse("Category added successfully", categoryDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            CategoryDTO categoryDTO = categoryService.convertToDto(category);
            return ResponseEntity.ok(new ApiResponse("Success", categoryDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateRequest request) {
        try {
            Category category = categoryService.updateCategory(request, id);
            CategoryDTO categoryDTO = categoryService.convertToDto(category);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", categoryDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categoryService.getConvertedCategories(categories);
        return ResponseEntity.ok(new ApiResponse("Success", categoryDTOs));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchCategoriesByName(@RequestParam String name) {
        List<Category> categories = categoryService.searchCategoriesByName(name);
        List<CategoryDTO> categoryDTOs = categoryService.getConvertedCategories(categories);
        return ResponseEntity.ok(new ApiResponse("Success", categoryDTOs));
    }
    
}
