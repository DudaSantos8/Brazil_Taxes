package com.br.API_Impostos_Brasil.services;

import com.br.API_Impostos_Brasil.controllers.dtos.CalculationTaxDto;
import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BrazilTaxesServiceTests {

    @Autowired
    private BrazilTaxesService service;
    private CalculationTaxDto calculationTaxDto;
    private List<TaxesDto> dtoList = new ArrayList<>();
    private TaxesDto taxesDto;

    @BeforeEach
    public void setUp(){
        this.calculationTaxDto = new CalculationTaxDto(3, 1000);

        this.taxesDto = new TaxesDto();
        taxesDto.setId(1);
        taxesDto.setName("IPI");
        taxesDto.setDescription("Imposto sobre Produtos Industrializados");
        taxesDto.setAliquota(12);
        dtoList.add(taxesDto);
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

    @Test
    public void testCaseRegisterTaxesOk(){
        TaxesDto dto = service.registerTax(taxesDto);
        assertEquals(taxesDto, dto);
    }

    @Test
    public void testCaseGetListOfTaxes(){
        service.registerTax(taxesDto);
        List<TaxesDto> list = service.getAllTaxes();
        assertTrue(list.equals(dtoList));
    }

}
