package com.example.un_app.models;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

public class YearData {
    private int year;
    @Field("gdp usd")
    private BigDecimal gdpUsd;

    @Field("current account balance")
    private BigDecimal currentAccountBalance;

    @Field("fdi net")
    private BigDecimal fdiNet;

    @Field("fdi net inflows")
    private BigDecimal fdiNetInflows;

    @Field("fdi net outflows")
    private BigDecimal fdiNetOutflows;

    @Field("gdp growth rate")
    private BigDecimal gdpGrowthRate;
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getGdpUsd() {
        return gdpUsd;
    }

    public void setGdpUsd(BigDecimal gdpUsd) {
        this.gdpUsd = gdpUsd;
    }

    public BigDecimal getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    public void setCurrentAccountBalance(BigDecimal currentAccountBalance) {
        this.currentAccountBalance = currentAccountBalance;
    }

    public BigDecimal getFdiNet() {
        return fdiNet;
    }

    public void setFdiNet(BigDecimal fdiNet) {
        this.fdiNet = fdiNet;
    }

    public BigDecimal getFdiNetInflows() {
        return fdiNetInflows;
    }

    public void setFdiNetInflows(BigDecimal fdiNetInflows) {
        this.fdiNetInflows = fdiNetInflows;
    }

    public BigDecimal getFdiNetOutflows() {
        return fdiNetOutflows;
    }

    public void setFdiNetOutflows(BigDecimal fdiNetOutflows) {
        this.fdiNetOutflows = fdiNetOutflows;
    }

    public BigDecimal getGdpGrowthRate() {
        return gdpGrowthRate;
    }

    public void setGdpGrowthRate(BigDecimal gdpGrowthRate) {
        this.gdpGrowthRate = gdpGrowthRate;
    }

    @Override
    public String toString() {
        return "YearData{" +
                "year=" + year +
                ", gdpUsd=" + gdpUsd +
                ", currentAccountBalance=" + currentAccountBalance +
                ", fdiNet=" + fdiNet +
                ", fdiNetInflows=" + fdiNetInflows +
                ", fdiNetOutflows=" + fdiNetOutflows +
                ", gdpGrowthRate=" + gdpGrowthRate +
                '}';
    }


    // Getters and setters
}
