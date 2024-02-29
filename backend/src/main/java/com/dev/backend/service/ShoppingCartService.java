package com.dev.backend.service;

import com.dev.backend.entity.ShoppingCart;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setCreationDate(new Date());
        return shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart, Long id) throws NotFoundException {
        try {
            shoppingCart.setId(id);
    
            @SuppressWarnings("null")
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);
            if (optionalShoppingCart.isPresent()) {
                ShoppingCart existingShoppingCart = optionalShoppingCart.get();
                Date recoveryCreationDate = existingShoppingCart.getCreationDate();
                shoppingCart.setCreationDate(recoveryCreationDate);
                shoppingCart.setUpdateDate(new Date());
                return shoppingCartRepository.saveAndFlush(shoppingCart);
            } else {
                throw new RuntimeException("ShoppingCart not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating shoppingCart with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deleteShoppingCart(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).get();
        shoppingCartRepository.delete(shoppingCart);
    }
}
