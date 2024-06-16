package com.be.two.c.apibetwoc.controller.historico;

import com.be.two.c.apibetwoc.controller.historico.dto.MetodoPagamentoHistoricoDto;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.controller.historico.dto.TransacaoHistoricoDto;
import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.service.venda.HistoricoVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historico-vendas")
@RequiredArgsConstructor
public class HistoricoController {

    private final HistoricoVendaService historicoVendaService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<TransacaoHistoricoDto>> getHistoricoVenda(@PathVariable Long id,
                                                                         @RequestParam(required = false,defaultValue = "0") int  page,
                                                                         @RequestParam(required = false,defaultValue = "10") int size) {
        Page<Transacao> transacoes = historicoVendaService.getHistoricoVenda(page, size, id);
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Page<TransacaoHistoricoDto> historico = transacoes
                .map(TransacaoHistoricoDto::new);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/filtro/{id}")
    public ResponseEntity<Page<TransacaoHistoricoDto>> getHistoricoPorFiltro(@PathVariable Long id,
                                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
                                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate,
                                                                             @RequestParam(required = false) String status,
                                                                             @RequestParam(required = false) String metodoPagamento,
                                                                             @RequestParam(required = false, defaultValue = "0") int page,
                                                                             @RequestParam(required = false, defaultValue = "10") int size) {
        Page<Transacao> transacoes = historicoVendaService
                .getHistoricoPorFiltro(de, ate, status, metodoPagamento, page, size, id);
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Page<TransacaoHistoricoDto> historicoComFiltro = transacoes
                .map(TransacaoHistoricoDto::new);
        return ResponseEntity.ok(historicoComFiltro);
    }

    @GetMapping("/{id}/metodos-pagamento")
    public ResponseEntity<List<MetodoPagamentoHistoricoDto>> listarMetodosPagamentoAceitos(@PathVariable Long id) {
        List<MetodoPagamentoAceito> metodosPagamento = historicoVendaService.listarMetodosPagamentoAceitos(id);
        if (metodosPagamento.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<MetodoPagamentoHistoricoDto> metodosPagamentoDto = metodosPagamento
                .stream()
                .map(MetodoPagamentoHistoricoDto::new).toList();
        return ResponseEntity.ok(metodosPagamentoDto);
    }

    @GetMapping("/{id}/download-txt")
    public ResponseEntity<byte[]> downloadTxt(@PathVariable Long id) {
        try {
            byte[] txtData = historicoVendaService.downloadTxt(id);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transacoes_" + id + ".txt");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");

            return new ResponseEntity<>(txtData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
