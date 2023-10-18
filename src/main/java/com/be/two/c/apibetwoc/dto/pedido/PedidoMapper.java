package com.be.two.c.apibetwoc.dto.pedido;

import com.be.two.c.apibetwoc.model.Pedido;

import java.time.LocalDateTime;

public class PedidoMapper {
    public static Pedido of(PedidoCriacaoDto pedidoCriacaoDto){
        Pedido pedido = new Pedido();
        pedido.setStatusDescricao("pendente");
        pedido.setNf(pedido.getNf());
        pedido.setDataHoraPedido(LocalDateTime.now());
        pedido.setIsPagamentoOnline(pedidoCriacaoDto.isPagamentoOnline());
        return pedido;
    }
}
