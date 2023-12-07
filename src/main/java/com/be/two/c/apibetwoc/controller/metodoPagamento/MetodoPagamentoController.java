package com.be.two.c.apibetwoc.controller.metodoPagamento;


import com.be.two.c.apibetwoc.controller.metodoPagamento.dto.MetodoPagamentoResponseDTO;
import com.be.two.c.apibetwoc.controller.metodoPagamento.mapper.MetodoPagamentoMapper;
import com.be.two.c.apibetwoc.model.MetodoPagamento;
import com.be.two.c.apibetwoc.service.metodoPagamento.MetodoPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metodos-pagamentos")
@RequiredArgsConstructor
public class MetodoPagamentoController {
    private final MetodoPagamentoService metodoPagamentoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagamentoResponseDTO>> listarMetodoPagamentos(){
        List<MetodoPagamento> metodosPagamentos = metodoPagamentoService.listarMetodosPagamentos();
        if(metodosPagamentos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(metodosPagamentos.stream().map(MetodoPagamentoMapper::toMetodoPagamentoResponse).toList());
    }


}
