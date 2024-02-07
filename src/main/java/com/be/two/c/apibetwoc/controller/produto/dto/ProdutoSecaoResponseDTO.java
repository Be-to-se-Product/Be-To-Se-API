package com.be.two.c.apibetwoc.controller.produto.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoSecaoResponseDTO {
    private Long id;
    private String descricao;
    private ProdutoEstabelecimentoResponseDTO estabelecimento;
}
