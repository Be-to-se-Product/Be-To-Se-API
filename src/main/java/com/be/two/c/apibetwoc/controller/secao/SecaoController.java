package com.be.two.c.apibetwoc.controller.secao;

import com.be.two.c.apibetwoc.controller.secao.dto.CadastroSecaoDto;
import com.be.two.c.apibetwoc.controller.secao.dto.ResponseSecaoDto;
import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
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
    public ResponseEntity<List<ResponseSecaoDto>> listarSecoes(){
        List<Secao> secoes = secaoService.listarSecoes();

        return secoes.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(secoes.stream().map(SecaoMapper::of).toList());
    }

    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<List<ResponseSecaoDto>> listarPorEstabelecimento(@PathVariable Long id){
        List<Secao> secoes = secaoService.listarPorEstabelecimento(id);

        List<ResponseSecaoDto> dtos = secoes.stream()
                .map(SecaoMapper::of)
                .toList();

        return dtos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(dtos);
    }

    @PostMapping
    public ResponseEntity<Secao> cadastrarSecao(@Valid @RequestBody CadastroSecaoDto secao){
        Secao secaoCadastrada = secaoService.cadastrarSecao(secao);

        return ResponseEntity.status(201).body(secaoCadastrada);
    }
}
