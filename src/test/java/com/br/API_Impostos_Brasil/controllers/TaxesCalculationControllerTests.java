package com.br.API_Impostos_Brasil.controllers;

import com.br.API_Impostos_Brasil.controllers.dtos.CalculationTaxDto;
import com.br.API_Impostos_Brasil.controllers.dtos.CalculationTaxResponseDto;
import com.br.API_Impostos_Brasil.services.BrazilTaxesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TaxesCalculationControlller.class)
public class TaxesCalculationControllerTests {

    @MockitoBean
    private BrazilTaxesService service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;
    private CalculationTaxResponseDto calculationTaxResponseDto;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();

        this.calculationTaxResponseDto = new CalculationTaxResponseDto();
        calculationTaxResponseDto.setTypeTax("IPI");
        calculationTaxResponseDto.setBaseValue(1000f);
        calculationTaxResponseDto.setAliquota(12f);
        calculationTaxResponseDto.setValueTax(120f);
    }

    @Test
    public void testCaseCorrectCalculation() throws Exception {
        CalculationTaxDto calculationTaxDto = new CalculationTaxDto(1, 1000f);
        String json = mapper.writeValueAsString(calculationTaxDto);

        Mockito.when(service.calculationTaxes(calculationTaxDto)).thenReturn(calculationTaxResponseDto);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/calculo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeTax", CoreMatchers.is(calculationTaxResponseDto.getTypeTax())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.baseValue", CoreMatchers.is(1000.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquota", CoreMatchers.is(12.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueTax", CoreMatchers.is(120.0)));

    }
}
