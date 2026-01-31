package com.app.parking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calculate")
public class CalculationController {

    @GetMapping()
    public BigDecimal priceCalculation() {

        return null;
    }


}
