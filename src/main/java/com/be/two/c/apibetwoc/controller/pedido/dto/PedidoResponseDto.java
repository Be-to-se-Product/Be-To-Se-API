package com.be.two.c.apibetwoc.controller.pedido.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PedidoResponseDto {
    private Long metodo;
    private Boolean isPagamentoOnline;
    private LocalDateTime dataHoraPedido;
}
