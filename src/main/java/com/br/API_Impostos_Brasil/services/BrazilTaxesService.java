package com.br.API_Impostos_Brasil.services;

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

    public float calculationTaxes(int id, int baseValue) {
        TaxesDto taxesDto = findById(id);
        float aliquota = taxesDto.getAliquota();
        return (baseValue * aliquota) /100;
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
