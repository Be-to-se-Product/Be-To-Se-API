package com.be.two.c.apibetwoc.controller.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoEstabelecimentoResponseDTO {
    private Long id;
    private String nome;
    private List<Long> idMetodo;
}
