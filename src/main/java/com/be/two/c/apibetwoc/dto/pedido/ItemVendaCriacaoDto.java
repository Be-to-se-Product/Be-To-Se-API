package com.be.two.c.apibetwoc.dto.pedido;

public record ItemVendaCriacaoDto(
        Long idConsumidor,
        Long idProduto,
        int quantidade,
        PedidoCriacaoDto pedido
) {
}
