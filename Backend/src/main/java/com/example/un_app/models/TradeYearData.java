package com.example.un_app.models;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

public class TradeYearData {
    private int year;

    @Field("GNI")
    private BigDecimal gni;

    @Field("Reserves")
    private BigDecimal reserves;

    @Field("total_debt")
    private BigDecimal totalDebt;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getGni() {
        return gni;
    }

    public void setGni(BigDecimal gni) {
        this.gni = gni;
    }

    public BigDecimal getReserves() {
        return reserves;
    }

    public void setReserves(BigDecimal reserves) {
        this.reserves = reserves;
    }

    public BigDecimal getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
        this.totalDebt = totalDebt;
    }

    // Getters and setters
}
