package com.be.two.c.apibetwoc.controller.estabelecimento.dto.catalogo;

import lombok.Data;

import java.util.List;

@Data
public class ProdutoCatalogoResponseDto {

    private Long id;
    private String nome;
    private String codigoSku;
    private Double preco;
    private String descricao;
    private Double precoOferta;
    private String codigoBarras;
    private String categoria;
    private Boolean isPromocaoAtiva;
    private Integer qtdVendido;
    private Double taxaCompra;
    List<String> imagens;
}
