package com.be.two.c.apibetwoc.controller.historico.dto;

import com.be.two.c.apibetwoc.model.ItemVenda;

public record ItemPedidoHistoricoDto(
        Integer id,
        String nome,
        int quantidade,
        Double preco
) {
    public ItemPedidoHistoricoDto(ItemVenda itemVenda) {
        this(itemVenda.getProduto().getId(),
                itemVenda.getProduto().getNome(),
                itemVenda.getQuantidade(),
                itemVenda.getProduto().getPreco());
    }
}
