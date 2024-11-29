package com.br.API_Impostos_Brasil.services;

import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
import org.springframework.stereotype.Service;

@Service
public class BrazilTaxesService {
    public TaxesDto registerTax(TaxesDto taxesDto) {
        return taxesDto;
    }

    public float calculationTaxes(int id, int baseValue) {
        return 0;
    }

    public TaxesDto findById(int id) {
        return null;
    }
}
