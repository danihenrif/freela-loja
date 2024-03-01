package com.dev.backend.service;

import com.dev.backend.entity.Category;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        category.setCreationDate(new Date());
        return categoryRepository.saveAndFlush(category);
    }

    public Category updateCategory(Category category, Long id) throws NotFoundException {
        try {
            category.setId(id);
    
            @SuppressWarnings("null")
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                Category existingCategory = optionalCategory.get();
                Date recoveryCreationDate = existingCategory.getCreationDate();
                category.setCreationDate(recoveryCreationDate);
                category.setUpdateDate(new Date());
                return categoryRepository.saveAndFlush(category);
            } else {
                throw new RuntimeException("Category not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating category with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
    }
}