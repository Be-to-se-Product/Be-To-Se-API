package com.be.two.c.apibetwoc.controller.metodopagamento;

import com.be.two.c.apibetwoc.controller.metodopagamento.dto.MetodoPagamentoResponseDto;
import com.be.two.c.apibetwoc.controller.metodopagamento.mapper.MetodoPagamentoMapper;
import com.be.two.c.apibetwoc.model.MetodoPagamento;
import com.be.two.c.apibetwoc.service.MetodoPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metodos-pagamento")
@RequiredArgsConstructor
public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagamentoResponseDto>> findAll() {
        List<MetodoPagamento> all = metodoPagamentoService.findAll();

        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<MetodoPagamentoResponseDto> metodoPagamentoResponseDtoList = all.stream()
                .map(MetodoPagamentoMapper::toMetodoPagamentoResponseDto).toList();

        return ResponseEntity.ok(metodoPagamentoResponseDtoList);
    }
}
