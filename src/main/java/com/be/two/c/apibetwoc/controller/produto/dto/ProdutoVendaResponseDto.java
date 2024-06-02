package com.be.two.c.apibetwoc.controller.produto.dto;

import lombok.Data;

@Data
public class ProdutoVendaResponseDto {
    private Long id;
    private String nome;
    private Double preco;
    private String imagem;
    private Integer qtd;
    private Long idEstabelecimento;
}
