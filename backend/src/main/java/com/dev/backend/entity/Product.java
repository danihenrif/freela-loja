package com.dev.backend.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String shortDescription;
    private String longDescription;
    private Double costValue;
    private Double saleValue;   
    private Date creationDate;
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "idBrand")
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "shoppingCart_product",
                joinColumns = @JoinColumn(name = "productId"),
                inverseJoinColumns = @JoinColumn(name = "shoppingCartId")
    )
    private Set<ShoppingCart> shopping_carts = new HashSet<>();

}
