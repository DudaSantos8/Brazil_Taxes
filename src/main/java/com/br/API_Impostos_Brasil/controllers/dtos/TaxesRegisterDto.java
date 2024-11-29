package com.br.API_Impostos_Brasil.controllers.dtos;

import lombok.Data;

@Data
public class TaxesRegisterDto {
    private String name;
    private String description;
    private float aliquota;

    public TaxesRegisterDto(String name, String description, float aliquota) {
        this.name = name;
        this.description = description;
        this.aliquota = aliquota;
    }
}
