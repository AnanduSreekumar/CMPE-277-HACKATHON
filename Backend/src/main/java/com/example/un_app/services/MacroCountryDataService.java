package com.example.un_app.services;

import com.example.un_app.models.MacroCountryData;
import com.example.un_app.models.YearData;
import com.example.un_app.models.YearValueDTO;
import com.example.un_app.repositories.MacroCountryDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MacroCountryDataService {
    @Autowired
    private MacroCountryDataRepository repository;

    public List<YearValueDTO> getFilteredData(String country, int startYear, int endYear, String filter) {
        List<MacroCountryData> countryDataList = repository.findByCountry(country);
        List<YearValueDTO> filteredData = new ArrayList<>();

        for (MacroCountryData countryData : countryDataList) {
            for (YearData yearData : countryData.getData()) {
                if (yearData.getYear() >= startYear && yearData.getYear() <= endYear) {
                    BigDecimal value = getValueByFilter(yearData, filter);
                    filteredData.add(new YearValueDTO(yearData.getYear(), value));
                }
            }
        }

        return filteredData;
    }

    private BigDecimal getValueByFilter(YearData yearData, String filter) {
        FilterType filterType;
        filterType = FilterType.valueOf(filter.toUpperCase());
        switch (filterType) {
            case GDP:
                return yearData.getGdpUsd();
            case CURRENT_ACCOUNT_BALANCE:
                return yearData.getCurrentAccountBalance();
            case FDI_NET:
                return yearData.getFdiNet();
            case FDI_INFLOWS:
                return yearData.getFdiNetInflows();
            case FDI_OUTFLOWS:
                return yearData.getFdiNetOutflows();
            case GDP_GROWTH_RATE:
                return yearData.getGdpGrowthRate();
            default:
                throw new IllegalArgumentException("Unknown filter: " + filter);
        }
    }
}
