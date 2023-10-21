package com.be.two.c.apibetwoc.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoCriacaoDto(
        @NotNull
        Long idMetodoPagamento,
        @NotBlank
        String nf,
        boolean isPagamentoOnline
) {
}
