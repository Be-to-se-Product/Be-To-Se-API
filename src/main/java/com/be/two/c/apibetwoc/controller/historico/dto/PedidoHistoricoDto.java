package com.be.two.c.apibetwoc.controller.historico.dto;

import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoHistoricoDto(Long id,
                                 LocalDate dataHoraPedido,
                                 StatusPedido statusDescricao,
                                 Boolean isPagamentoOnline,
                                 LocalDateTime dataHoraRetirada,
                                 String cpfCliente,
                                 List<ItemPedidoHistoricoDto> itens
) {
    public PedidoHistoricoDto(Pedido pedido) {
        this(pedido.getId(),
                pedido.getDataHoraPedido().toLocalDate(),
                pedido.getStatusDescricao(),
                pedido.getIsPagamentoOnline(),
                pedido.getDataHoraRetirada(),
                pedido.getItens()
                        .get(0)
                        .getConsumidor()
                        .getCpf(),
                pedido.getItens()
                        .stream()
                        .map(ItemPedidoHistoricoDto::new)
                        .toList());
    }
}
