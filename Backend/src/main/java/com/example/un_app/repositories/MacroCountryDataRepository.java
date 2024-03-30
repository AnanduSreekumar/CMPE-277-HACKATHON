package com.example.un_app.repositories;

import com.example.un_app.models.MacroCountryData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MacroCountryDataRepository extends MongoRepository<MacroCountryData,String> {
    List<MacroCountryData> findByCountry(String country);
}
