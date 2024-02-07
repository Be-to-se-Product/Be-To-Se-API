package com.be.two.c.apibetwoc.controller.produto.dto;

import com.be.two.c.apibetwoc.service.tag.dto.TagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDetalhamentoDto {
    private Long id;
    private String nome;
    private String codigoSku;
    private Double preco;
    private String descricao;
    private Double precoOferta;
    private String codigoBarras;
    private String categoria;
    private Boolean isAtivo;
    private Boolean isPromocaoAtiva;
    private List<String> imagens;
    private ProdutoSecaoResponseDTO secao;
    private List<TagResponseDTO> tags;

}
