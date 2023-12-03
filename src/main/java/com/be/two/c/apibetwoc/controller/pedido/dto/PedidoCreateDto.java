package com.be.two.c.apibetwoc.controller.pedido.dto;

import lombok.Data;

import java.util.List;
@Data
public class PedidoCreateDto {
    private Long idConsumidor;
    private Long idEstabelecimento;
    private List<ItemVendaCreateDto>itens;
    private MetodoDto metodo;
}
