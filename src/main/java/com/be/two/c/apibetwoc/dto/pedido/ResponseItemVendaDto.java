package com.be.two.c.apibetwoc.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseItemVendaDto {
    private Long id;
    private int quantidade;
    private String produtoNome;
    private Double preco;

}
