package com.dev.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.backend.entity.ProductImage;
import com.dev.backend.service.ProductImageService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/productImage")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/")
    public List<ProductImage> getAll() {
        return productImageService.getAll();
    }

    @GetMapping("/productImage/{id}")
    public List<ProductImage> getAll(@PathVariable Long id) {
        return productImageService.getImageByProduct(id);
    }

    @PostMapping("/{id}")
    public ProductImage addProductImage(@PathVariable Long id, @RequestParam MultipartFile file) {
        return productImageService.addProductImage(id, file);
    }

    /*@PutMapping("/{id}")
    public ProductImage updateProductImage(@RequestBody ProductImage productImage, @PathVariable Long id) throws Exception {
        return productImageService.updateProductImage(productImage, id);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long id) {
        productImageService.deleteProductImage(id);
        return ResponseEntity.ok().build();
    }
}
