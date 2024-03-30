package com.example.un_app.models;

import java.math.BigDecimal;

public class YearValueDTO {
    private int year;
    private BigDecimal value;

    public YearValueDTO(int year, BigDecimal value) {
        this.year = year;
        this.value = value;
    }

    // Getters and Setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

