package com.br.API_Impostos_Brasil.controllers;

import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
import com.br.API_Impostos_Brasil.controllers.dtos.TaxesRegisterDto;
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

@WebMvcTest(BrazilTaxesController.class)
public class BrazilTaxesControllerTests {
    @MockitoBean
    private BrazilTaxesService service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;
    private TaxesDto taxesDto;
    private CalculationTaxResponseDto calculationTaxResponseDto;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();

        this.taxesDto = new TaxesDto();
        taxesDto.setId(1);
        taxesDto.setName("IPI");
        taxesDto.setDescription("Imposto sobre Produtos Industrializados");
        taxesDto.setAliquota(12);

        this.calculationTaxResponseDto = new CalculationTaxResponseDto();
        calculationTaxResponseDto.setTypeTax("IPI");
        calculationTaxResponseDto.setbaseValue(1000);
        calculationTaxResponseDto.setAliquota(12);
        calculationTaxResponseDto.setValueTax(120);

    }

    @Test
    public void testCaseErrorFreeTaxRegister() throws Exception {

        TaxesRegisterDto registerDto = new TaxesRegisterDto("IPI", "Imposto sobre Produtos Industrializados", 12);
        String json = mapper.writeValueAsString(registerDto);

        Mockito.when(service.registerTax(Mockito.any(TaxesDto.class))).thenReturn(taxesDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/tipos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(taxesDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("IPI")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("Imposto sobre Produtos Industrializados")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquota", CoreMatchers.is(12.0)));
    }

    // tests for get by id and calculation
    @Test
    public void testCaseSearchTaxById() throws Exception {
        Mockito.when(service.findById(taxesDto.getId())).thenReturn(taxesDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/tipos"+ taxesDto.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(taxesDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("IPI")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("Imposto sobre Produtos Industrializados")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquota", CoreMatchers.is(12.0)));
    }

    @Test
    public void testCaseCorrectCalculation() throws Exception {
        CalculationTaxDto calculationTaxDto = new CalculationTaxDto(1, 1000);
        String json = mapper.writeValueAsString(calculationTaxDto);

        Mockito.when(service.calculationTaxes(Mockito.any(CalculationTaxDto.class))).thenReturn(calculationTaxResponseDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/calculo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.typeTax", CoreMatchers.is(calculationTaxResponseDto.getTypeTax())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.baseValue", CoreMatchers.is(calculationTaxResponseDto.getBaseValue)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquota", CoreMatchers.is(calculationTaxResponseDto.getAliquota)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueTax", CoreMatchers.is(calculationTaxResponseDto.getValueTax)));

    }
}
