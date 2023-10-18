package com.be.two.c.apibetwoc.dto.pedido;

public record PedidoCriacaoDto(
 Long idMetodoPagamento,
 String nf,
 boolean isPagamentoOnline
) {
}
