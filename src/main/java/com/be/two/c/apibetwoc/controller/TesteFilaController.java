package com.be.two.c.apibetwoc.controller;


import com.be.two.c.apibetwoc.service.TesteFilaService;
import com.be.two.c.apibetwoc.util.FilaRequisicao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TesteFilaController {
    private final TesteFilaService testeFilaService;
    private final FilaRequisicao filaRequisicao;
    @PutMapping
    public void teste(){
        filaRequisicao.entrarFila();
        testeFilaService.atualizar();
        filaRequisicao.sairFila();

    }
}
