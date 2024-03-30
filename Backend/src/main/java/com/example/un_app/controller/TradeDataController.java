package com.example.un_app.controller;

import com.example.un_app.models.YearValueDTO;
import com.example.un_app.services.TradeDataService;
import com.example.un_app.models.TradeYearData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeDataController {

    @Autowired
    private TradeDataService service;

    @GetMapping("/data")
    public List<YearValueDTO> getTradeData(
            @RequestParam String country,
            @RequestParam int startYear,
            @RequestParam int endYear,
            @RequestParam String filter) {

        return service.getFilteredData(country, startYear, endYear, filter);
    }
}
