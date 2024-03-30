package com.example.un_app.services;

import com.example.un_app.models.AgricultureCountryData;
import com.example.un_app.models.AgricultureYearData;
import com.example.un_app.models.YearValueDTO;
import com.example.un_app.repositories.AgricultureDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgricultureDataService {
    @Autowired
    private AgricultureDataRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(AgricultureDataService.class);

    public List<YearValueDTO> getFilteredData(String country, int startYear, int endYear, String filter) {
        List<AgricultureCountryData> agricultureDataList = repository.findByCountry(country);
        System.out.println("Retrieved data: " + agricultureDataList);

        List<YearValueDTO> filteredData = new ArrayList<>();

        for (AgricultureCountryData agricultureData : agricultureDataList) {
            for (AgricultureYearData yearData : agricultureData.getData()) {
                System.out.println("Year Data {}"+yearData);
                if (yearData.getYear() >= startYear && yearData.getYear() <= endYear) {
                    BigDecimal value = getValueByFilter(yearData, filter);
                    System.out.println("Filtered value for year " + yearData.getYear() + ": " + value);
                    filteredData.add(new YearValueDTO(yearData.getYear(), value));
                }
            }
        }

        return filteredData;
    }

    private BigDecimal getValueByFilter(AgricultureYearData yearData, String filter) {
        FilterType filterType;
        filterType = FilterType.valueOf(filter.toUpperCase());
        switch (filterType) {
            case CONTRIBUTION_TO_GDP:
                return yearData.getContributionToGdp();
            case FERTILIZERS:
                return yearData.getFertilizers();
            case FERTILIZERS_PRODUCTION:
                return yearData.getFertilizerProduction();
            default:
                throw new IllegalArgumentException("Unknown filter: " + filter);
        }
    }
}
