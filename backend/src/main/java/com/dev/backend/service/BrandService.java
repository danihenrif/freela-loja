package com.dev.backend.service;

import com.dev.backend.entity.Brand;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    public Brand addBrand(Brand brand) {
        brand.setCreationDate(new Date());
        return brandRepository.saveAndFlush(brand);
    }

    public Brand updateBrand(Brand brand, Long id) throws NotFoundException {
        try {
            brand.setId(id);
    
            @SuppressWarnings("null")
            Optional<Brand> optionalBrand = brandRepository.findById(id);
            if (optionalBrand.isPresent()) {
                Brand existingBrand = optionalBrand.get();
                Date recoveryCreationDate = existingBrand.getCreationDate();
                brand.setCreationDate(recoveryCreationDate);
                brand.setUpdateDate(new Date());
                return brandRepository.saveAndFlush(brand);
            } else {
                throw new RuntimeException("Brand not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating brand with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).get();
        brandRepository.delete(brand);
    }
}
