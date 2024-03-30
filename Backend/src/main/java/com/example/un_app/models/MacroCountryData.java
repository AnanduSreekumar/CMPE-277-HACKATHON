package com.example.un_app.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "Macroeconomic_Table")
public class MacroCountryData {
    private String country;
    private List<YearData> data;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<YearData> getData() {
        return data;
    }

    public void setData(List<YearData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MacroCountryData{" +
                "country='" + country + '\'' +
                ", data=" + data +
                '}';
    }

    // Getters and setters
}

