package com.placidotech.pasteleria.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.ProductDTO;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.request.AddProductRequest;
import com.placidotech.pasteleria.request.ProductUpdateRequest;
import com.placidotech.pasteleria.response.ApiResponse;
import com.placidotech.pasteleria.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




/**
 *
 * @author CristopherPlacidoOca
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){
        Product product = productService.addProduct(request);
        ProductDTO productDTO = productService.convertToDto(product);
        return ResponseEntity.ok(new ApiResponse("Product added successfully", productDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDTO productDTO = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        try {
            Product product = productService.updateProduct(request, id);
            ProductDTO productDTO = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success", productDTOs));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProductsByName(@RequestParam String name){
        List<Product> products = productService.searchProductsByName(name);
        List<ProductDTO> productDTOs = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success", productDTOs));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        List<ProductDTO> productDTOs = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success", productDTOs));
    }
    
}
