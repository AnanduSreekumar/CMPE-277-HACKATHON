package com.example.un_app.controller;

import com.example.un_app.models.CountryValueDTO;
import com.example.un_app.models.YearData;
import com.example.un_app.models.YearValueDTO;
import com.example.un_app.services.MacroCountryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MacroCountryDataController {

    @Autowired
    private MacroCountryDataService service;

    @GetMapping("/data")
    public List<YearValueDTO> getMacroCountryData(
            @RequestParam String country,
            @RequestParam int startYear,
            @RequestParam int endYear,
            @RequestParam String filter) {

        return service.getFilteredData(country, startYear, endYear, filter);
    }
}

