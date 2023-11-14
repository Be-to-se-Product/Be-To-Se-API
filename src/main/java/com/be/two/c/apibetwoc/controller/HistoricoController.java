package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.historico.TransacaoHistoricoDto;
import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.service.HistoricoVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historico-vendas")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoVendaService historicoVendaService;

    @GetMapping("/{id}")
    public ResponseEntity<List<TransacaoHistoricoDto>> getHistoricoVenda(@PathVariable Long id,
                                                                         @RequestParam(required = false) @DefaultValue(value = "0") int page,
                                                                         @RequestParam(required = false) @DefaultValue(value = "10") int size) {
        List<Transacao> transacoes = historicoVendaService.getHistoricoVenda(page, size, id).toList();
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<TransacaoHistoricoDto> historico = transacoes
                .stream()
                .map(TransacaoHistoricoDto::new).toList();
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/filtro/{id}")
    public ResponseEntity<Page<TransacaoHistoricoDto>> getHistoricoPorFiltro(@PathVariable Long id,
                                                                             @RequestParam(required = false) LocalDateTime de,
                                                                             @RequestParam(required = false) LocalDateTime ate,
                                                                             @RequestParam(required = false) String status,
                                                                             @RequestParam(required = false) String metodoPagamento,
                                                                             @RequestParam(required = false) @DefaultValue("0") int page,
                                                                             @RequestParam(required = false) @DefaultValue("25") int size) {
        Page<Transacao> transacoes = historicoVendaService
                .getHistoricoPorFiltro(de, ate, status, metodoPagamento, page, size, id);
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Page<TransacaoHistoricoDto> historicoComFiltro = transacoes
                .map(TransacaoHistoricoDto::new);
        return ResponseEntity.ok(historicoComFiltro);
    }
}
