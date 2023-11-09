package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.historico.TransacaoHistoricoDto;
import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.service.HistoricoVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoVendaService historicoVendaService;

    @GetMapping
    public ResponseEntity<List<TransacaoHistoricoDto>> getHistoricoVenda(@RequestParam(required = false)  @DefaultValue(value = "0") int page,
                                                                         @RequestParam(required = false) @DefaultValue(value = "10") int size) {
        List<Transacao> transacoes = historicoVendaService.getHistoricoVenda(page, size).toList();
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TransacaoHistoricoDto> historico = transacoes
                .stream()
                .map(TransacaoHistoricoDto::new).toList();
        return ResponseEntity.ok(historico);
    }
}
