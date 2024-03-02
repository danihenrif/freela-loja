package com.dev.backend.service;

import com.dev.backend.entity.Product;
import com.dev.backend.entity.ProductImage;

import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.backend.repository.ProductImageRepository;
import com.dev.backend.repository.ProductRepository;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ProductImage> getAll() {
        return productImageRepository.findAll();
    }

    public List<ProductImage> getImageByProduct(Long id) {
        List<ProductImage> listProductImage = productImageRepository.findByProductId(id);
        
        for (ProductImage productImage : listProductImage) {
            try (InputStream in = new FileInputStream("C:\\Users\\Daniel\\Documents\\github\\freela-loja\\backend\\src\\main\\resources\\"+productImage.getName())) {
                productImage.setArchive(IOUtils.toByteArray(in));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return listProductImage;
    }

    public ProductImage addProductImage(Long idProduct, MultipartFile file) {
        @SuppressWarnings("null")
        Product product = productRepository.findById(idProduct).get();
        ProductImage object = new ProductImage();

        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                String imageName = String.valueOf(product.getId()) + file.getOriginalFilename();
                Path path = Paths
                        .get("C:\\Users\\Daniel\\Documents\\github\\freela-loja\\backend\\src\\main\\resources\\"
                                + imageName);
                Files.write(path, bytes);
                object.setName(imageName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        object.setProduct(product);
        object.setCreationDate(new Date());
        object = productImageRepository.saveAndFlush(object);
        return object;
    }

    /*
     * public ProductImage updateProductImage(ProductImage productImage, Long id)
     * throws NotFoundException {
     * try {
     * productImage.setId(id);
     * 
     * @SuppressWarnings("null")
     * Optional<ProductImage> optionalProductImage =
     * productImageRepository.findById(id);
     * if (optionalProductImage.isPresent()) {
     * ProductImage existingProductImage = optionalProductImage.get();
     * Date recoveryCreationDate = existingProductImage.getCreationDate();
     * productImage.setCreationDate(recoveryCreationDate);
     * productImage.setUpdateDate(new Date());
     * return productImageRepository.saveAndFlush(productImage);
     * } else {
     * throw new RuntimeException("ProductImage not found with id: " + id);
     * }
     * } catch (Exception e) {
     * throw new RuntimeException("Error updating productImage with id: " + id, e);
     * }
     * }
     */

    @SuppressWarnings("null")
    public void deleteProductImage(Long id) {
        ProductImage productImage = productImageRepository.findById(id).get();
        productImageRepository.delete(productImage);
    }
}
