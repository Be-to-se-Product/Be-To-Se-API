package com.be.two.c.apibetwoc.dto.pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemVendaCriacaoDto(
        @NotNull
        Long idConsumidor,
        @NotNull
        Long idProduto,
        @Min(value = 1)
        int quantidade,
        @NotNull
        PedidoCriacaoDto pedido
) {
}
