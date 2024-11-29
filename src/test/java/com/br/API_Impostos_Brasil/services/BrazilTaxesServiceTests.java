package com.br.API_Impostos_Brasil.services;

import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
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

    private TaxesDto taxesDto;

    @BeforeEach
    public void setUp(){
        this.taxesDto = new TaxesDto();
        taxesDto.setId(1);
        taxesDto.setName("IPI");
        taxesDto.setDescription("Imposto sobre Produtos Industrializados");
        taxesDto.setAliquota(12);
    }

    @Test
    public void testCaseCorrectCalculation(){
        float result = service.calculationTaxes(1, 1000);
        assertEquals(120, result);
    }

    @Test
    public void testCaseIncorrectId(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.calculationTaxes(3, 1000));
        assertEquals("Tax type not found", exception.getMessage());
    }

    @Test
    public void testCaseSearchTaxById(){
        TaxesDto dtoList = service.findById(1);
        assertEquals(taxesDto, dtoList);
    }

    @Test
    public void testCaseSearchTaxWithIncorrectId(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findById(3));
        assertEquals("Tax type not found", exception.getMessage());
    }

}
