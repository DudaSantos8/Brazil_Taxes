package com.br.API_Impostos_Brasil.controllers.dtos;

import lombok.Data;

@Data
public class TaxesDto {
    private int id;
    private String name;
    private String description;
    private float aliquota;
}
