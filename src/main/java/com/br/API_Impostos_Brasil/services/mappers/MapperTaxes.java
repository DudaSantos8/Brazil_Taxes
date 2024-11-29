package com.br.API_Impostos_Brasil.services.mappers;

import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
import com.br.API_Impostos_Brasil.controllers.dtos.TaxesRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperTaxes {
    public static TaxesDto parseToTaxesDto(TaxesRegisterDto registerDto) {
        ObjectMapper mapper = new ObjectMapper();

        TaxesDto dto = mapper.convertValue(registerDto, TaxesDto.class);
        dto.setId((int) Math.random());
        return dto;
    }
}
