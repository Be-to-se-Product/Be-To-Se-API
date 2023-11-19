package com.be.two.c.apibetwoc.controller.historico.dto;

import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;

import java.time.LocalDateTime;

public record PedidoHistoricoDto(Long id,
                                 LocalDateTime dataHoraPedido,
                                 StatusPedido statusDescricao,
                                 Boolean isPagamentoOnline,
                                 LocalDateTime dataHoraRetirada
) {
    public PedidoHistoricoDto(Pedido pedido) {
        this(pedido.getId(), pedido.getDataHoraPedido(),
                pedido.getStatusDescricao(),
                pedido.getIsPagamentoOnline(),
                pedido.getDataHoraRetirada());
    }
}
