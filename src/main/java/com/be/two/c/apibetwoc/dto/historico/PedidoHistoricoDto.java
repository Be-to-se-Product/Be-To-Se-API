package com.be.two.c.apibetwoc.dto.historico;

import com.be.two.c.apibetwoc.model.Pedido;

import java.time.LocalDateTime;

public record PedidoHistoricoDto(Long id,
                                 LocalDateTime dataHoraPedido,
                                 String statusDescricao,
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
