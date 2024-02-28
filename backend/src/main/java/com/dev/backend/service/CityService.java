package com.dev.backend.service;

import com.dev.backend.entity.City;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.dev.backend.repository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public City addCity(City city) {
        city.setCreationDate(new Date());
        return cityRepository.saveAndFlush(city);
    }

    public City updateCity(City city, Long id) throws NotFoundException {
        try {
            city.setId(id);
    
            @SuppressWarnings("null")
            Optional<City> optionalCity = cityRepository.findById(id);
            if (optionalCity.isPresent()) {
                City existingCity = optionalCity.get();
                Date recoveryCreationDate = existingCity.getCreationDate();
                city.setCreationDate(recoveryCreationDate);
                city.setUpdatedDate(new Date());
                return cityRepository.saveAndFlush(city);
            } else {
                throw new RuntimeException("City not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating city with id: " + id, e);
        }
    }

    @SuppressWarnings("null")
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id).get();
        cityRepository.delete(city);
    }
}
