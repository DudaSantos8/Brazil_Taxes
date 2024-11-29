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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(BrazilTaxesController.class)
public class BrazilTaxesControllerTests {
    @MockitoBean
    private BrazilTaxesService service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;
    private TaxesDto taxesDto;
    private List<TaxesDto> dtoList = new ArrayList<>();


    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();

        this.taxesDto = new TaxesDto();
        taxesDto.setId(1);
        taxesDto.setName("IPI");
        taxesDto.setDescription("Imposto sobre Produtos Industrializados");
        taxesDto.setAliquota(12);
        dtoList.add(taxesDto);
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
                                .get("/tipos/"+ taxesDto.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(taxesDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("IPI")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("Imposto sobre Produtos Industrializados")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aliquota", CoreMatchers.is(12.0)));
    }

@Test
public void testCaseGetAllTaxes() throws Exception {
    Mockito.when(service.getAllTaxes()).thenReturn(dtoList);

    mockMvc.perform(
            MockMvcRequestBuilders
                    .get("/tipos")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(dtoList.size()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(dtoList.get(0).getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(dtoList.get(0).getName()));
}
}
