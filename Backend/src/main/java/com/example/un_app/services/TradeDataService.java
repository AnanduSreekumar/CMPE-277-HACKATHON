package com.example.un_app.services;

import com.example.un_app.models.TradeData;
import com.example.un_app.models.TradeYearData;
import com.example.un_app.models.YearValueDTO;
import com.example.un_app.repositories.TradeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeDataService {
    @Autowired
    private TradeDataRepository repository;

    public List<YearValueDTO> getFilteredData(String country, int startYear, int endYear, String filter) {
        List<TradeData> tradeDataList = repository.findByCountry(country);
        List<YearValueDTO> filteredData = new ArrayList<>();

        for (TradeData tradeData : tradeDataList) {
            for (TradeYearData yearData : tradeData.getData()) {
                if (yearData.getYear() >= startYear && yearData.getYear() <= endYear) {
                    BigDecimal value = getValueByFilter(yearData, filter);
                    filteredData.add(new YearValueDTO(yearData.getYear(), value));
                }
            }
        }

        return filteredData;
    }

    private BigDecimal getValueByFilter(TradeYearData yearData, String filter) {
        FilterType filterType;
        filterType = FilterType.valueOf(filter.toUpperCase());
        switch (filterType) {
            case GNI:
                return yearData.getGni();
            case RESERVES:
                return yearData.getReserves();
            case TOTAL_DEBT:
                return yearData.getTotalDebt();
            default:
                throw new IllegalArgumentException("Unknown filter: " + filter);
        }
    }
}
