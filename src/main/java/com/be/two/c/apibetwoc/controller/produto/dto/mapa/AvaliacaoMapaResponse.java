package com.be.two.c.apibetwoc.controller.produto.dto.mapa;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoMapaResponse {
    private String usuario;
    private Integer qtdEstrela;
    private LocalDate data;
    private String descricao;
}
