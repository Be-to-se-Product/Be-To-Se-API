package com.be.two.c.apibetwoc.controller.pedido.mapper;

import com.be.two.c.apibetwoc.controller.pedido.dto.*;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;

import java.time.LocalDateTime;

public class PedidoMapper {
    public static Pedido of(PedidoCriacaoDto pedidoCriacaoDto) {
        Pedido pedido = new Pedido();
        pedido.setNf(pedidoCriacaoDto.nf());
        pedido.setDataHoraPedido(LocalDateTime.now());
        pedido.setIsPagamentoOnline(pedidoCriacaoDto.isPagamentoOnline());
        return pedido;
    }

    public static Pedido of(MetodoDto metodoDto){
        Pedido pedido = new Pedido();
        pedido.setIsPagamentoOnline(metodoDto.getIsPagamentoOnline());
        pedido.setDataHoraPedido(LocalDateTime.now());
        pedido.setStatusDescricao(StatusPedido.PENDENTE);
        return pedido;
    }

    public static ResponsePedidoDTO of(Pedido pedido) {
        ResponsePedidoDTO responsePedidoDTO = new ResponsePedidoDTO(pedido.getId(), pedido.getDataHoraPedido(), pedido.getStatusDescricao(), pedido.getIsPagamentoOnline(), pedido.getMetodoPagamentoAceito().getMetodoPagamento().getDescricao(), pedido.getItens().stream().map(ItemVendaMapper::of).toList());
        return responsePedidoDTO;
    }

    public static PedidoResponseDto ofTeste(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.setMetodo(pedido.getMetodoPagamentoAceito().getId());
        dto.setDataHoraPedido(pedido.getDataHoraPedido());
        dto.setIsPagamentoOnline(pedido.getIsPagamentoOnline());
        return dto;
    }

    public static ResponsePedidoConsumidorDto ofResponseUsuario(Pedido pedido) {
        return new ResponsePedidoConsumidorDto(pedido.getId(), pedido.getDataHoraPedido(), pedido.getStatusDescricao(), pedido.getIsPagamentoOnline(), pedido.getMetodoPagamentoAceito().getMetodoPagamento().getDescricao(), pedido.getItens().stream().map(ItemVendaMapper::of).toList(), new EstabelecimentoResponsePedido(pedido.getMetodoPagamentoAceito().getEstabelecimento().getNome(), new EnderecoResponsePedido(pedido.getMetodoPagamentoAceito().getEstabelecimento().getEndereco().getRua(), pedido.getMetodoPagamentoAceito().getEstabelecimento().getEndereco().getNumero(), pedido.getMetodoPagamentoAceito().getEstabelecimento().getEndereco().getBairro())));
    }


}
