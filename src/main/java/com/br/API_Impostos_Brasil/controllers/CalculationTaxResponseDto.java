package com.br.API_Impostos_Brasil.controllers;

import lombok.Data;

@Data
public class CalculationTaxResponseDto {
    private String typeTax;
    private float baseValue;
    private float aliquota;
    private float valueTax;
}
