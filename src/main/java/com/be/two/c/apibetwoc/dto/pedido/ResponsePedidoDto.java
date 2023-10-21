package com.be.two.c.apibetwoc.dto.pedido;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePedidoDto {
    private Long id;
    private String nf;
    private String statusDescricao;
    private Boolean isPagamentoOnline;
    private List<ResponseItemVendaDto> itens;
}
