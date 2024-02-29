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
@Table(name = "person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String cpf;
    private String email;
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "idCity")
    private City city; 
    
    @ManyToMany
    @JoinTable(name = "permission_person", 
                joinColumns = @JoinColumn(name = "personId"), 
                inverseJoinColumns = @JoinColumn( name = "permissionId"))
    private Set<Permission> permissions = new HashSet<>();

    private String cep;
    private Date creationDate;
    private Date updateDate;
}
