package com.be.two.c.apibetwoc.controller.produto.dto.mapa;

import com.be.two.c.apibetwoc.service.ITempoPercurso;
import lombok.Data;

import java.util.List;


@Data
public class ProdutoMapaResponseDTO {
    private Long id;
    private String nome;
    private String categoria;
    private String descricao;
    private Double precoAtual;
    private Double precoAntigo;
    private List<String> imagens;
    private List<AvaliacaoMapaResponse> avaliacao;
    private Double mediaAvaliacao;
    private EstabelecimentoMapaResponse estabelecimento;



}
