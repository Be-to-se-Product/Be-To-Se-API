package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.consumidor.ConsumidorCriacaoDto;
import com.be.two.c.apibetwoc.dto.consumidor.ResponseConsumidorDto;
import com.be.two.c.apibetwoc.service.ConsumidorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("consumidores")
@RequiredArgsConstructor
public class ConsumidorController {

    private final ConsumidorService consumidorService;

    @PostMapping
    public ResponseEntity<ResponseConsumidorDto> cadastrar(@Valid @RequestBody ConsumidorCriacaoDto consumidorCriacaoDto,
                                                           UriComponentsBuilder uriBuilder) {
        ResponseConsumidorDto consumidor = consumidorService.cadastrar(consumidorCriacaoDto);
        URI uri = uriBuilder.path("{id}").buildAndExpand(consumidor.getId()).toUri();
        return ResponseEntity.created(uri).body(consumidor);
    }

    @GetMapping
    public ResponseEntity<List<ResponseConsumidorDto>> listar() {
        List<ResponseConsumidorDto> consumidores = consumidorService.listar();
        if (consumidores.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(consumidores);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseConsumidorDto> buscarPorId(Long id) {
        return ResponseEntity.ok(consumidorService.buscarPorId(id));
    }
}
