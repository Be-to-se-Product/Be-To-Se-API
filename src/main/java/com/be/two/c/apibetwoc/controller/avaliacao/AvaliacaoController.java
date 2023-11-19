package com.be.two.c.apibetwoc.controller.avaliacao;

import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoRequestDTO;
import com.be.two.c.apibetwoc.controller.avaliacao.mapper.AvaliacaoMapper;
import com.be.two.c.apibetwoc.model.Avaliacao;
import com.be.two.c.apibetwoc.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Avaliacao>> avaliacaoPorProduto(@PathVariable Long id){
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacaoPorProduto(id);

        if (avaliacoes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(avaliacoes);
    }
    @PostMapping
    public ResponseEntity<Avaliacao>publicarAvaliacao(@Valid @RequestBody AvaliacaoRequestDTO avaliacaoRequestDTO){

        return ResponseEntity.ok(avaliacaoService.publicar(avaliacaoRequestDTO));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void>editarAvaliacao(@RequestParam String comentario, @RequestParam Integer qtdEstrela, @PathVariable Long id){
        avaliacaoService.editar(comentario,qtdEstrela,id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletarAvaliacao(@PathVariable Long id){
        avaliacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
