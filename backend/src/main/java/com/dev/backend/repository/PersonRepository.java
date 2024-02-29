package com.dev.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.backend.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
