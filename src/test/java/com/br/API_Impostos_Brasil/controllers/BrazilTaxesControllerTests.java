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

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();

        this.taxesDto = new TaxesDto();
        taxesDto.setId(1);
        taxesDto.setName("IPI");
        taxesDto.setDescription("Imposto sobre Produtos Industrializados");
        taxesDto.setAliquota(12);
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
/*    @Test
    public void testCaseSearchTaxById(){
        TaxesDto taxesDtoResponse = service.findById(1);
        assertEquals(taxesDto, taxesDtoResponse);
    }

    @Test
    public void testCaseCorrectCalculation(){
        float result = service.calculationTaxes(taxesDto.getId(), 1000);
        assertEquals(120, result);
    }*/
}
