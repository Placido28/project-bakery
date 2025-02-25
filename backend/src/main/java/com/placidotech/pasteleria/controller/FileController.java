package com.placidotech.pasteleria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.placidotech.pasteleria.model.Product;
import com.placidotech.pasteleria.repository.ProductRepository;
import com.placidotech.pasteleria.service.FirebaseStorageService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author CristopherPlacidoOca
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
public class FileController {

    private final FirebaseStorageService firebaseStorageService;
    private final ProductRepository productRepository;

    @PostMapping("/upload/{productId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long productId, @RequestParam("file") MultipartFile file) {
        try{
            Optional<Product> productOpt = productRepository.findById(productId);
            if(productOpt.isPresent()){
                Product product = productOpt.get();
                String imageUrl = firebaseStorageService.uploadFile(file);

                product.setImagenUrl(imageUrl);
                productRepository.save(product); //Guarda URL en la BD

                return ResponseEntity.ok("Imagen subida y vinculada: " + imageUrl);
            } else {
                return ResponseEntity.status(404).body("Producto no encontrado");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir la imagen");
        }
    }
    
}
