package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.CadastroEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.service.EstabelecimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {


    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ResponseEntity<List<Estabelecimento>> listar() {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarTodos();
        if (estabelecimentos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estabelecimentos);
    }

    @PostMapping()
    public ResponseEntity<Estabelecimento> cadastrarEstabelecimento(@Valid @RequestBody CadastroEstabelecimentoDto estabelecimento)//, @PathVariable Comerciante comerciante)
    {
        //estabelecimento.setComerciante(comerciante);// Em processo de criação; aguardando criação de Controllers para listagem a partir deles, aomente assim para fazer validações.
        return ResponseEntity.status(201).body(estabelecimentoService.cadastroEstabelecimento(estabelecimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estabelecimento> atualizarEstabelecimento(@Valid @RequestBody CadastroEstabelecimentoDto estabelecimentoDto, @PathVariable Long id) {
        return estabelecimentoService.atualizar(estabelecimentoDto,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estabelecimento> deletarEstabelecimento(@PathVariable Long id){
        return estabelecimentoService.deletar(id);
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
