package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.estabelecimento.dto.AtualizarEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.estabelecimento.dto.CadastroEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.dto.estabelecimento.dto.ResponseEstabelecimentoDto;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.service.EstabelecimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
@RequiredArgsConstructor
public class EstabelecimentoController {

    private final EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ResponseEntity<List<Estabelecimento>> listarTodos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarTodos();

        return estabelecimentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(estabelecimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estabelecimento> listarPorId(@PathVariable Long id){
        return ResponseEntity.status(200).body(estabelecimentoService.listarPorId(id));
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ResponseEstabelecimentoDto> listarPorId2(@PathVariable Long id){
        return ResponseEntity.status(200).body(estabelecimentoService.listarPorId2(id));
    }

    @GetMapping("/segmento")
    public ResponseEntity<List<Estabelecimento>> listarPorSegmento(@RequestParam String segmento){
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarPorSegmento(segmento);

        return estabelecimentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(estabelecimentos);
    }

    @PostMapping()
    public ResponseEntity<Estabelecimento> cadastrarEstabelecimento(@Valid @RequestBody CadastroEstabelecimentoDto estabelecimento) {
        return ResponseEntity.status(201).body(estabelecimentoService.cadastroEstabelecimento(estabelecimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> atualizarEstabelecimento(@Valid @RequestBody AtualizarEstabelecimentoDto estabelecimentoDto, @PathVariable Long id) {
        return ResponseEntity.status(200).body(estabelecimentoService.atualizarEstabelecimento(estabelecimentoDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstabelecimento(@PathVariable Long id){
        estabelecimentoService.deletar(id);
        return ResponseEntity.status(204).build();
    }


    @GetMapping("/rota-pessoa")
    public Long calcularRotaPessoa(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaPessoa(coordenadaDto);
    }

    @GetMapping("/rota-bicicleta")
    public Long calcularRotaBicicleta(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaBicicleta(coordenadaDto);
    }

    @GetMapping("/rota-carro")
    public Long calcularRotaCarro(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaCarro(coordenadaDto);
    }

}
