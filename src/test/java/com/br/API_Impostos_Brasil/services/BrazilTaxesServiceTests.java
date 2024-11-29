package com.br.API_Impostos_Brasil.services;

import com.br.API_Impostos_Brasil.controllers.CalculationTaxDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BrazilTaxesServiceTests {

    @Autowired
    private BrazilTaxesService service;
    private CalculationTaxDto calculationTaxDto;

    @BeforeEach
    public void setUp(){
        this.calculationTaxDto = new CalculationTaxDto(3, 1000);
    }

    @Test
    public void testCaseIncorrectId(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.calculationTaxes(calculationTaxDto));
        assertEquals("Tax type not found", exception.getMessage());
    }

    @Test
    public void testCaseSearchTaxWithIncorrectId(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findById(calculationTaxDto.getTypeTaxId()));
        assertEquals("Tax type not found", exception.getMessage());
    }

}
