package com.be.two.c.apibetwoc.dto.historico;

import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;

public record MetodoPagamentoHistoricoDto(
        Long id,
        String descricao
) {
    public MetodoPagamentoHistoricoDto(MetodoPagamentoAceito metodoPagamentoAceito) {
        this(metodoPagamentoAceito
                        .getId(),
                metodoPagamentoAceito
                        .getMetodoPagamento()
                        .getDescricao());
    }
}
