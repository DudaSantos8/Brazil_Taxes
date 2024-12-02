package com.br.API_Impostos_Brasil.controllers.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaxesRegisterDto {
    @NotNull(message = "this field cant be null")
    @NotEmpty(message = "this field cant be empty")
    private String name;

    @NotNull(message = "this field cant be null")
    @NotEmpty(message = "this field cant be empty")
    private String description;

    @NotNull(message = "this field cant be null")
    @NotEmpty(message = "this field cant be empty")
    private float aliquota;

    public TaxesRegisterDto(String name, String description, float aliquota) {
        this.name = name;
        this.description = description;
        this.aliquota = aliquota;
    }
}
