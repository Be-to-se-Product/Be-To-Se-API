package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoRequestDTO;
import com.be.two.c.apibetwoc.controller.carrinho.mapper.CarrinhoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Carrinho;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.CarrinhoRepository;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;

    private final ConsumidorRepository consumidorRepository;

    private final ProdutoRepository produtoRepository;
    public Carrinho adicionar(CarrinhoRequestDTO carrinho, LocalDateTime dtH){

        Consumidor consumidor = consumidorRepository.findById(carrinho.getConsumidor())
                .orElseThrow(() -> new EntidadeNaoExisteException("Consumidor não encontrado"));
        Produto produto = produtoRepository.findById(carrinho.getProduto())
                .orElseThrow(() -> new EntidadeNaoExisteException("Produto não encontrado"));
        return carrinhoRepository.save(CarrinhoMapper.toCarrinho(carrinho,produto,consumidor));
    }
    public List<Carrinho> carrinhoDoConsumidor(Long id){
        Consumidor consumidor = consumidorRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Consumidor não encontrado")
        );
        List<Carrinho> carrinho = carrinhoRepository.carrinhoDoConsumidor(consumidor);

        return carrinho;
    }
    public void editar(Long id, Integer quantidade){
        Carrinho carrinho = carrinhoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Carrinho não encontrado")
        );
        LocalDateTime dtH = LocalDateTime.now();
        carrinhoRepository.editarCarrinho(id,dtH,quantidade);
    }
    public void esvaziarCarrinho(Long id){
        Consumidor consumidor = consumidorRepository.findById(id).orElseThrow(
                ()-> new EntidadeNaoExisteException("Consumidor não encontrado")
        );
        carrinhoRepository.esvaziarCarrinho(consumidor);
    }
    public void removerProduto(Long id){
        Carrinho carrinho = carrinhoRepository.findById(id).orElseThrow(
                ()-> new EntidadeNaoExisteException("Carrinho não encontrado")
        );
        carrinhoRepository.deleteById(id);
    }
}
