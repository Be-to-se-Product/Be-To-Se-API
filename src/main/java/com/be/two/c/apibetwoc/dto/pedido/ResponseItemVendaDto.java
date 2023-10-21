package com.be.two.c.apibetwoc.dto.pedido;

import lombok.Data;

@Data
public class ResponseItemVendaDto {

    private Long id;
    private int quantidade;
    private String produtoNome;
}
