package com.placidotech.pasteleria.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placidotech.pasteleria.dto.ProductDTO;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.mapper.ProductMapper;
import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.repository.ProductRepository;
import com.placidotech.pasteleria.request.AddProductRequest;
import com.placidotech.pasteleria.request.ProductUpdateRequest;
import com.placidotech.pasteleria.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{

    private final ProductRepository productRepository;
    private final ICategoryService categoryService;

    @Override
    @Transactional
    public Product addProduct(AddProductRequest product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        newProduct.setImagenUrl(product.getImageUrl());
        newProduct.setCategory(categoryService.getCategoryById(product.getCategoryId()));
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setRemoved(true); //Soft delete
        productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(ProductUpdateRequest product, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setImagenUrl(product.getImageUrl());
        existingProduct.setCategory(categoryService.getCategoryById(product.getCategoryId()));
        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByRemovedFalse();
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingAndRemovedFalse(name);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndRemovedFalse(categoryId);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO convertToDto(Product product) {
        return ProductMapper.toDTO(product);
    }

    
}
