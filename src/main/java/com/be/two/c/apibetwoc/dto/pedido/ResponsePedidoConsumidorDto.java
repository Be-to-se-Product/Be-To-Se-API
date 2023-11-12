package com.be.two.c.apibetwoc.dto.pedido;

import com.be.two.c.apibetwoc.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record ResponsePedidoConsumidorDto(Long id, LocalDateTime dataPedido, StatusPedido statusPedido, Boolean isPagamentoOnline ,
                                          String metodoPagamento, List<ResponseItemVendaDto> itens, EstabelecimentoResponsePedido estabelecimento) { }
