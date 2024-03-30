package com.example.un_app.controller;

import com.example.un_app.models.AgricultureYearData;
import com.example.un_app.models.YearValueDTO;
import com.example.un_app.services.AgricultureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/agriculture")
public class AgricultureDataController {

    @Autowired
    private AgricultureDataService service;

    @GetMapping("/data")
    public List<YearValueDTO> getAgricultureData(
            @RequestParam String country,
            @RequestParam int startYear,
            @RequestParam int endYear,
            @RequestParam String filter) {

        return service.getFilteredData(country, startYear, endYear, filter);
    }
}

