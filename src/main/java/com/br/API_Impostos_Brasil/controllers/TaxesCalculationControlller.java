package com.br.API_Impostos_Brasil.controllers;

import com.br.API_Impostos_Brasil.controllers.dtos.CalculationTaxDto;
import com.br.API_Impostos_Brasil.controllers.dtos.CalculationTaxResponseDto;
import com.br.API_Impostos_Brasil.services.BrazilTaxesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/calculo")
public class TaxesCalculationControlller {

    @Autowired
    private BrazilTaxesService service;

    @PostMapping
    public ResponseEntity<?> doCalculationOfTax(@RequestBody CalculationTaxDto taxDto){
        try{
            return ResponseEntity.status(200).body(service.calculationTaxes(taxDto));
        }catch (Exception exception){
            return ResponseEntity.status(404).body(Map.of("message", exception.getMessage()));
        }
    }
}
