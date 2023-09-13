package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.service.EstabelecimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @PostMapping("/rota-pessoa")
    public Long calcularRotaPessoa(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaPessoa(coordenadaDto);
    }

    @PostMapping("/rota-bicicleta")
    public Long calcularRotaBicicleta(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaBicicleta(coordenadaDto);
    }

    @PostMapping("/rota-carro")
    public Long calcularRotaCarro(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaCarro(coordenadaDto);
    }

}
