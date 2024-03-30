package com.example.un_app.models;

import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

public class AgricultureYearData {
    private int year;

    @Field("Contribution to GDP")
    private BigDecimal contributionToGdp;

    @Field("Fertilizers")
    private BigDecimal fertilizers;

    @Field("Fertilizer Production")
    private BigDecimal fertilizerProduction;

    public BigDecimal getContributionToGdp() {
        return contributionToGdp;
    }

    public void setContributionToGdp(BigDecimal contributionToGdp) {
        this.contributionToGdp = contributionToGdp;
    }

    public BigDecimal getFertilizers() {
        return fertilizers;
    }

    public void setFertilizers(BigDecimal fertilizers) {
        this.fertilizers = fertilizers;
    }

    public BigDecimal getFertilizerProduction() {
        return fertilizerProduction;
    }

    public void setFertilizerProduction(BigDecimal fertilizerProduction) {
        this.fertilizerProduction = fertilizerProduction;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "YearData{" +
                "year=" + year +
                ", contributionToGdp=" + contributionToGdp +
                ", fertilizers=" + fertilizers +
                ", fertilizerProduction=" + fertilizerProduction +
                '}';
    }
    // Getters and setters
}
