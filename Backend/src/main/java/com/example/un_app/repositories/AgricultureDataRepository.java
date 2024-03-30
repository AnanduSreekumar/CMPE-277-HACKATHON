package com.example.un_app.repositories;

import com.example.un_app.models.AgricultureCountryData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AgricultureDataRepository extends MongoRepository<AgricultureCountryData, String> {
    List<AgricultureCountryData> findByCountry(String country);
}

