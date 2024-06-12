package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoRequestDTO;
import com.be.two.c.apibetwoc.controller.carrinho.mapper.CarrinhoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ConsumidorRepository consumidorRepository;
    private final ProdutoRepository produtoRepository;
    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    public Carrinho adicionar(CarrinhoRequestDTO carrinho, LocalDateTime dtH){

        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(EntidadeNaoExisteException::new);

        Consumidor consumidor = consumidorRepository.findById(usuario.getConsumidor().getId())
                .orElseThrow(() -> new EntidadeNaoExisteException("Consumidor não encontrado"));
        Produto produto = produtoRepository.findById(carrinho.getProduto())
                .orElseThrow(() -> new EntidadeNaoExisteException("Produto não encontrado"));

        Carrinho existe = carrinhoRepository.carrinhoDoConsumidorPorProduto(produto, consumidor);

        if (existe != null){
            return editar(existe.getId(), (carrinho.getQuantidade() + existe.getQuantidade()));
        }
        return carrinhoRepository.save(CarrinhoMapper.toCarrinho(carrinho,produto,consumidor));
    }
    public List<Carrinho> carrinhoDoConsumidor(){

        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(EntidadeNaoExisteException::new);

        Consumidor consumidor = consumidorRepository.findById(usuario.getConsumidor().getId()).orElseThrow(
                ()->new EntidadeNaoExisteException("Consumidor não encontrado")
        );
        List<Carrinho> carrinho = carrinhoRepository.carrinhoDoConsumidor(consumidor);

        return carrinho;
    }
    public Carrinho editar(Long id, Integer quantidade){
        Carrinho carrinho = carrinhoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Carrinho não encontrado")
        );

        if(quantidade.equals(0)){
            removerProduto(id);
            return null;
        }

        LocalDateTime dtH = LocalDateTime.now();
        carrinho.setQuantidade(quantidade);
        carrinho.setDataHoraAlocacao(dtH);
        carrinhoRepository.save(carrinho);
        return carrinho;
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

    public List<Carrinho> carrinhoDoConsumidorPorEstabelecimento(Long idEstabelecimento)
    {

        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(EntidadeNaoExisteException::new);

        Consumidor consumidor = consumidorRepository.findById(usuario.getConsumidor().getId()).orElseThrow(
                ()->new EntidadeNaoExisteException("Consumidor não encontrado")
        );

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(
                ()->new EntidadeNaoExisteException("Consumidor não encontrado")
        );

        List<Carrinho> carrinhos = carrinhoDoConsumidor();
        List<Carrinho> filtrado = carrinhos.stream().filter(carrinho -> carrinho.getProduto().getSecao().getEstabelecimento() == estabelecimento).collect(Collectors.toList());

        return filtrado;
    }
}
