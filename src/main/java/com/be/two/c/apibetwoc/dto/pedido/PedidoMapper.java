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

    public static ResponsePedidoDTO of(Pedido pedido) {
       ResponsePedidoDTO responsePedidoDTO = new ResponsePedidoDTO(pedido.getId(),pedido.getDataHoraPedido(), pedido.getStatusDescricao(),pedido.getIsPagamentoOnline(),pedido.getMetodoPagamentoAceito().getMetodoPagamento().getDescricao(),pedido.getItens().stream().map(ItemVendaMapper::of).toList());
       return responsePedidoDTO;
    }


}
