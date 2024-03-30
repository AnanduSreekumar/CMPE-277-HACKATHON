package com.example.un_app.models;

import java.math.BigDecimal;

public class CountryValueDTO {
    private String country;
    private BigDecimal value;

    public CountryValueDTO(String country, BigDecimal value) {
        this.country = country;
        this.value = value;
    }

    // Getters and Setters
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
