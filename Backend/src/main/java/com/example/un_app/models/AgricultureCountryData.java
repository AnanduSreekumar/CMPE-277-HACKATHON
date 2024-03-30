package com.example.un_app.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.util.List;

@Document(collection = "Agriculture_Table")
public class AgricultureCountryData {

    private String country;
    private List<AgricultureYearData> data;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<AgricultureYearData> getData() {
        return data;
    }

    public void setData(List<AgricultureYearData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AgricultureCountryData{" +
                "country='" + country + '\'' +
                ", data=" + data +
                '}';
    }

    // Getters and setters
}

