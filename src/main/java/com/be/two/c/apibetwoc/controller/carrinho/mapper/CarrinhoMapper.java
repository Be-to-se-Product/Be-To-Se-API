package com.be.two.c.apibetwoc.controller.carrinho.mapper;

import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoRequestDTO;
import com.be.two.c.apibetwoc.model.Carrinho;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;

import java.time.LocalDateTime;

public class CarrinhoMapper {

    public static Carrinho toCarrinho(CarrinhoRequestDTO carrinhoRequestDTO, Produto produto, Consumidor consumidor){
        return new Carrinho(null, LocalDateTime.now(),carrinhoRequestDTO.getQuantidade(),produto,consumidor);
    }
}
