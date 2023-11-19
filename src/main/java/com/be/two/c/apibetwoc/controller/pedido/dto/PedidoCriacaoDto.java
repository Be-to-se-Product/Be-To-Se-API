package com.be.two.c.apibetwoc.controller.pedido.dto;

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
