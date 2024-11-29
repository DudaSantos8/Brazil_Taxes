package com.br.API_Impostos_Brasil.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BrazilTaxesServiceTests {

    @Autowired
    private BrazilTaxesService service;

    @Test
    public void testCaseIncorrectId(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.calculationTaxes(3, 1000));
        assertEquals("Tax type not found", exception.getMessage());
    }

    @Test
    public void testCaseSearchTaxWithIncorrectId(){
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findById(3));
        assertEquals("Tax type not found", exception.getMessage());
    }

}
