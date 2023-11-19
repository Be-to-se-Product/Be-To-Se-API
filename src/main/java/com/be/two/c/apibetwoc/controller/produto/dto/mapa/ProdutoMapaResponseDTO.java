package com.be.two.c.apibetwoc.controller.produto.dto.mapa;

import lombok.Data;

import java.util.List;


@Data
public class ProdutoMapaResponseDTO {
    private Long id;
    private String nome;
    private String categoria;
    private String descricao;
    private List<String> imagens;
    private List<AvaliacaoMapaResponse> avaliacao;
    private Double mediaAvaliacao;
    private EstabelecimentoMapaResponse estabelecimento;

}
