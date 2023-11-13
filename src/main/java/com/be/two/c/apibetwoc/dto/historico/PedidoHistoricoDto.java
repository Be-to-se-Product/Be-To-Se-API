package com.be.two.c.apibetwoc.dto.historico;

import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;

import java.time.LocalDateTime;

public record PedidoHistoricoDto(Long id,
                                 LocalDateTime dataHoraPedido,
                                 StatusPedido statusDescricao,
                                 Boolean isPagamentoOnline,
                                 LocalDateTime dataHoraRetirada,
                                 String cpfCliente
) {
    public PedidoHistoricoDto(Pedido pedido) {
        this(pedido.getId(),
                pedido.getDataHoraPedido(),
                pedido.getStatusDescricao(),
                pedido.getIsPagamentoOnline(),
                pedido.getDataHoraRetirada(),
                pedido.getItens()
                        .get(0)
                        .getConsumidor()
                        .getCpf());
    }
}
