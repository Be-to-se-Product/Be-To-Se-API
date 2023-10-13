package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.comerciante.ComercianteCriacaoDto;
import com.be.two.c.apibetwoc.dto.comerciante.ComercianteMapper;
import com.be.two.c.apibetwoc.dto.comerciante.ResponseComercianteDto;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.service.ComercianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("comerciantes")
@RequiredArgsConstructor
public class ComercianteController {

    private final ComercianteService comercianteService;

    @PostMapping
    public ResponseEntity<ResponseComercianteDto> cadastrar(@RequestBody @Valid ComercianteCriacaoDto comercianteCriacaoDto){
        Comerciante comerciante = comercianteService.cadastrar(comercianteCriacaoDto);
        ResponseComercianteDto responseComercianteDto = ComercianteMapper.of(comerciante);
        URI uri = UriComponentsBuilder.fromPath("{id}").buildAndExpand(responseComercianteDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseComercianteDto);
    }
}
