package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.secao.CadastroSecaoDto;
import com.be.two.c.apibetwoc.model.Secao;
import com.be.two.c.apibetwoc.service.SecaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secoes")
@RequiredArgsConstructor
public class SecaoController {
    private final SecaoService secaoService;

    @GetMapping
    public ResponseEntity<List<Secao>> listarSecoes(){
        List<Secao> secoes = secaoService.listarSecoes();

        return secoes.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(secoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Secao>> listarPorEstabelecimento(@PathVariable Long id){
        List<Secao> secoes = secaoService.listarPorEstabelecimento(id);

        return secoes.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(secoes);
    }

    @PostMapping
    public ResponseEntity<Secao> cadastrarSecao(@Valid @RequestBody CadastroSecaoDto secao){
        Secao secaoCadastrada = secaoService.cadastrarSecao(secao);

        return ResponseEntity.status(201).body(secaoCadastrada);
    }
}
