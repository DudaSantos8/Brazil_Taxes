package com.br.API_Impostos_Brasil.controllers;

import com.br.API_Impostos_Brasil.controllers.dtos.TaxesDto;
import com.br.API_Impostos_Brasil.controllers.dtos.TaxesRegisterDto;
import com.br.API_Impostos_Brasil.services.BrazilTaxesService;
import com.br.API_Impostos_Brasil.services.mappers.MapperTaxes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tipos")
public class BrazilTaxesController {

    @Autowired
    private BrazilTaxesService service;

    @PostMapping
    public ResponseEntity<?> regiterBrazilTaxes(@RequestBody TaxesRegisterDto registerDto){
        try{
            return ResponseEntity.status(201).body(service.registerTax(MapperTaxes.parseToTaxesDto(registerDto)));
        }catch (Exception exception){
            return ResponseEntity.status(404).body(Map.of("message", exception.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrazilTaxesById(@PathVariable int id){
        try {
            return ResponseEntity.status(200).body(service.findById(id));
        }catch (Exception exception){
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping
    public List<TaxesDto> getAllTaxes(){
        return service.getAllTaxes();
    }
}
