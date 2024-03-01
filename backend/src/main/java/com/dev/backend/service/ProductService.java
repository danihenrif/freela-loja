package com.dev.backend.service;

import com.dev.backend.entity.Product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        product.setCreationDate(new Date());
        return productRepository.saveAndFlush(product);
    }

    public Product updateProduct(Product product, Long id) throws NotFoundException {
        try {
            product.setId(id);
    
            @SuppressWarnings("null")
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product existingProduct = optionalProduct.get();
                Date recoveryCreationDate = existingProduct.getCreationDate();
                product.setCreationDate(recoveryCreationDate);
                product.setUpdateDate(new Date());
                return productRepository.saveAndFlush(product);
            } else {
                throw new RuntimeException("Product not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating product with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }
}
