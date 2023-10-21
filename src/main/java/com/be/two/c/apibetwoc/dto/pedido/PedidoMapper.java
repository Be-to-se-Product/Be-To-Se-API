package com.be.two.c.apibetwoc.dto.pedido;

import com.be.two.c.apibetwoc.model.Pedido;

import java.time.LocalDateTime;

public class PedidoMapper {
    public static Pedido of(PedidoCriacaoDto pedidoCriacaoDto) {
        Pedido pedido = new Pedido();
        pedido.setNf(pedidoCriacaoDto.nf());
        pedido.setDataHoraPedido(LocalDateTime.now());
        pedido.setIsPagamentoOnline(pedidoCriacaoDto.isPagamentoOnline());
        return pedido;
    }

    public static ResponsePedidoDto of(Pedido pedido) {
        ResponsePedidoDto responsePedidoDto = new ResponsePedidoDto();
        responsePedidoDto.setId(pedido.getId());
        responsePedidoDto.setNf(pedido.getNf());
        responsePedidoDto.setStatusDescricao(pedido.getStatusDescricao());
        responsePedidoDto.setIsPagamentoOnline(pedido.getIsPagamentoOnline());
        responsePedidoDto.setItens(pedido
                .getItens()
                .stream()
                .map(ItemVendaMapper::of)
                .toList());
        return responsePedidoDto;
    }
}
