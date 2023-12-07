package com.be.two.c.apibetwoc.controller.pedido.dto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseItemVendaDto {
    private Long id;
    private int quantidade;
    ProdutoDetalhamentoDto produto;



}
