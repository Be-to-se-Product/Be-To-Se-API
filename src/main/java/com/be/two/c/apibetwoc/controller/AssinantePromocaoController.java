package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.AssinantePromocaoDto;
import com.be.two.c.apibetwoc.model.AssinantePromocao;
import com.be.two.c.apibetwoc.service.AssinantePromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinantespromocao")
public class AssinantePromocaoController {
    @Autowired
    private AssinantePromocaoService assinPromoService;

    @GetMapping
    private ResponseEntity<List<AssinantePromocao>> listar() {
        return assinPromoService.listarTodos();
    }

    @PostMapping
    private ResponseEntity<AssinantePromocao> cadastrarNovo(@RequestBody AssinantePromocaoDto assinantePromocaoDto){
        return assinPromoService.cadastrarNovo(assinantePromocaoDto);
    }
}
