package com.be.two.c.apibetwoc.dto.produto;

import com.be.two.c.apibetwoc.dto.MetodoPagamentoAceitoResponseDto;
import com.be.two.c.apibetwoc.model.Produto;

import java.util.List;

public class ProdutoResponseDto {
    private ProdutoDetalhamentoDto produtoDetalhamentoDto;
    private List<MetodoPagamentoAceitoResponseDto> metodos;

    public ProdutoResponseDto(Produto produto, List<MetodoPagamentoAceitoResponseDto> metodos) {
        this.produtoDetalhamentoDto = new ProdutoDetalhamentoDto(produto);
        this.metodos = metodos;
    }
}
