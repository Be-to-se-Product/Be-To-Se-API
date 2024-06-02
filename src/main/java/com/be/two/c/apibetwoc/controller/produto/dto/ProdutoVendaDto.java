package com.be.two.c.apibetwoc.controller.produto.dto;

import lombok.Data;

@Data
public class ProdutoVendaDto {
    private Long idProduto;
    private String imagem;
    private Integer quantidade;
    private String origem;
}
