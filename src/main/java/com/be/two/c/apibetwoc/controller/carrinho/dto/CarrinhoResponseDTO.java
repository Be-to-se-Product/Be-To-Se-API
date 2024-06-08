package com.be.two.c.apibetwoc.controller.carrinho.dto;

import com.be.two.c.apibetwoc.controller.produto.dto.mapa.ProdutoCarrinhoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoResponseDTO {
    private Long id;
    private Integer quantidade;
    private ProdutoCarrinhoResponseDTO produto;
    private String nomeEmpresa;
}
