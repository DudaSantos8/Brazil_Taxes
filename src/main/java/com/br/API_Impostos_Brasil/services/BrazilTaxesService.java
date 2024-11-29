package com.br.API_Impostos_Brasil.services;

import com.br.API_Impostos_Brasil.controllers.CalculationTaxDto;
import com.br.API_Impostos_Brasil.controllers.CalculationTaxResponseDto;
import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrazilTaxesService {
    private List<TaxesDto> dtoList = new ArrayList<>();

    public TaxesDto registerTax(TaxesDto taxesDto) {
        dtoList.add(taxesDto);
        return taxesDto;
    }

    public CalculationTaxResponseDto calculationTaxes(CalculationTaxDto calculationTaxDto) {
        TaxesDto taxesDto = findById(calculationTaxDto.getTypeTaxId());
        float calculation = (taxesDto.getAliquota() * calculationTaxDto.getBaseValue()) / 100;

        CalculationTaxResponseDto responseDto = new CalculationTaxResponseDto();

        responseDto.setTypeTax(taxesDto.getName());
        responseDto.setBaseValue(calculationTaxDto.getBaseValue());
        responseDto.setAliquota(taxesDto.getAliquota());
        responseDto.setValueTax(calculation);

        return responseDto;
    }

    public TaxesDto findById(int id) {
        for (TaxesDto taxesDto : dtoList){
            int idDto = taxesDto.getId();
            if(idDto == id){
                return taxesDto;
            }
            System.out.println(idDto);
        }
        throw new RuntimeException("Tax type not found");
    }
}
