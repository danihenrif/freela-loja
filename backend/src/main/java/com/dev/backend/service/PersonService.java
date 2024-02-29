package com.dev.backend.service;

import com.dev.backend.entity.Person;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person addPerson(Person person) {
        person.setCreationDate(new Date());
        return personRepository.saveAndFlush(person);
    }

    public Person updatePerson(Person person, Long id) throws NotFoundException {
        try {
            person.setId(id);
    
            @SuppressWarnings("null")
            Optional<Person> optionalPerson = personRepository.findById(id);
            if (optionalPerson.isPresent()) {
                Person existingPerson = optionalPerson.get();
                Date recoveryCreationDate = existingPerson.getCreationDate();
                person.setCreationDate(recoveryCreationDate);
                person.setUpdateDate(new Date());
                return personRepository.saveAndFlush(person);
            } else {
                throw new RuntimeException("Person not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating person with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id).get();
        personRepository.delete(person);
    }
}
