package com.be.two.c.apibetwoc.controller.carrinho.mapper;

import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoRequestDTO;
import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoResponseDTO;
import com.be.two.c.apibetwoc.controller.produto.dto.mapa.ProdutoCarrinhoResponseDTO;
import com.be.two.c.apibetwoc.model.Carrinho;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;

import java.time.LocalDateTime;

public class CarrinhoMapper {

    public static Carrinho toCarrinho(CarrinhoRequestDTO carrinhoRequestDTO, Produto produto, Consumidor consumidor){
        return new Carrinho(null, LocalDateTime.now(),carrinhoRequestDTO.getQuantidade(),produto,consumidor);
    }

    public static CarrinhoResponseDTO toDto(Carrinho carrinho){
        CarrinhoResponseDTO dto = new CarrinhoResponseDTO();
        ProdutoCarrinhoResponseDTO dtoProduto = new ProdutoCarrinhoResponseDTO();

        dtoProduto.setId(carrinho.getProduto().getId()
        );
        dtoProduto.setNome(carrinho.getProduto().getNome());
        dtoProduto.setImagens(carrinho.getProduto().getImagens().stream().map(element -> element.getNomeReferencia()).toList());
        dtoProduto.setPreco(carrinho.getProduto().getPreco());

        dto.setId(carrinho.getId());
        dto.setNomeEmpresa(carrinho.getProduto().getSecao().getEstabelecimento().getNome());
        dto.setQuantidade(carrinho.getQuantidade());
        dto.setProduto(dtoProduto);
        return dto;
    }
}
