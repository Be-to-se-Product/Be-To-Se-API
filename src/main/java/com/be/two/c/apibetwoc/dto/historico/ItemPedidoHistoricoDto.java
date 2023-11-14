package com.be.two.c.apibetwoc.dto.historico;

import com.be.two.c.apibetwoc.model.ItemVenda;

public record ItemPedidoHistoricoDto(
        Long id,
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
