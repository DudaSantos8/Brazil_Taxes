package com.br.API_Impostos_Brasil.controllers;

import lombok.Data;

@Data
public class CalculationTaxDto {
    private int typeTaxId;
    private float baseValue;

    public CalculationTaxDto(int typeTaxId, float baseValue) {
        this.typeTaxId = typeTaxId;
        this.baseValue = baseValue;
    }
}
