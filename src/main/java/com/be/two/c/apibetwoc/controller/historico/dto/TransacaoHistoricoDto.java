package com.be.two.c.apibetwoc.controller.historico.dto;

import com.be.two.c.apibetwoc.model.Transacao;

public record TransacaoHistoricoDto(Long id,
                                    Double valor,
                                    Double taxa,
                                    boolean isEstornado,
                                    String nomeMetodoPagamento,
                                    PedidoHistoricoDto pedido) {

    public TransacaoHistoricoDto(Transacao transacao) {
        this(transacao.getId(), transacao.getValor(), transacao.getTaxa(), transacao.isEstornado(),
                transacao.getPedido().getMetodoPagamentoAceito().getMetodoPagamento().getDescricao(), new PedidoHistoricoDto(transacao.getPedido()));
    }
}
