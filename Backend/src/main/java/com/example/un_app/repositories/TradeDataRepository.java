package com.example.un_app.repositories;

import com.example.un_app.models.TradeData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TradeDataRepository extends MongoRepository<TradeData, String> {
    List<TradeData> findByCountry(String country);
}
