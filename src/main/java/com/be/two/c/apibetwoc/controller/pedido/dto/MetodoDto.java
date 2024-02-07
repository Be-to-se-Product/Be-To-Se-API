package com.be.two.c.apibetwoc.controller.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MetodoDto {
    @NotNull
    private Long idMetodoPagamento;
    @NotNull
    private Boolean isPagamentoOnline;
}
