package com.example.un_app.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.util.List;

@Document(collection = "Trade_Table")
public class TradeData {
    private String country;
    private List<TradeYearData> data;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<TradeYearData> getData() {
        return data;
    }

    public void setData(List<TradeYearData> data) {
        this.data = data;
    }

    // Getters and setters
}

