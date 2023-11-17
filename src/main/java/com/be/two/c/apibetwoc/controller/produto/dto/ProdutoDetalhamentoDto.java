package com.be.two.c.apibetwoc.controller.produto.dto;

import com.be.two.c.apibetwoc.dto.secao.SecaoDetalhamentoDto;
import com.be.two.c.apibetwoc.model.Produto;
import lombok.Data;

import java.util.List;

@Data
public class ProdutoDetalhamentoDto {

    private Integer id;
    private String nome;
    private String codigoSku;
    private Double preco;
    private String descricao;
    private Double precoOferta;
    private String codigoBarras;
    private String categoria;
    private Boolean isAtivo;
    private Boolean isPromocaoAtiva;
    private String imagem;
    private SecaoDetalhamentoDto secao;

    public ProdutoDetalhamentoDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.codigoSku = produto.getCodigoSku();
        this.preco = produto.getPreco();
        this.descricao = produto.getDescricao();
        this.precoOferta = produto.getPrecoOferta();
        this.codigoBarras = produto.getCodigoBarras();
        this.categoria = produto.getCategoria();
        this.isAtivo = produto.getIsAtivo();
        this.isPromocaoAtiva = produto.getIsPromocaoAtiva();
        this.imagem = produto.getImagens().get(0).getNomeImagem();
        this.secao = new SecaoDetalhamentoDto(produto.getSecao());
    }
}
