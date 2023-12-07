package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.pedido.dto.ItemVendaCreateDto;
import com.be.two.c.apibetwoc.controller.pedido.dto.ItemVendaCriacaoDto;
import com.be.two.c.apibetwoc.controller.pedido.dto.ItemVendaMapper;
import com.be.two.c.apibetwoc.controller.pedido.dto.PedidoCreateDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.ItemVenda;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.repository.ItemVendaRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final PedidoService pedidoService;
    private final ConsumidorRepository consumidorRepository;
    private final ProdutoRepository produtoRepository;
    private final TransacaoService transacaoService;
    private final CarrinhoService carrinhoService;

    public Pedido cadastrar(PedidoCreateDto pedidoCreate ){
        List<ItemVenda> itensVendas = new ArrayList<>();
        Pedido pedido = pedidoService.cadastrar(pedidoCreate.getMetodo());
        for (ItemVendaCreateDto i : pedidoCreate.getItens()){
            ItemVenda itemVenda = ItemVendaMapper.of(i);
            Consumidor consumidor = consumidorRepository.findById(pedidoCreate.getIdConsumidor())
                    .orElseThrow(() -> new EntidadeNaoExisteException("Consumidor informado não existe"));
            Produto produto = produtoRepository.findById(i.getIdProduto())
                    .orElseThrow(() -> new EntidadeNaoExisteException("Produto informado não existe"));
            itemVenda.setConsumidor(consumidor);
            itemVenda.setProduto(produto);
            itemVenda.setPedido(pedido);
            itemVenda.setPromocaoAtiva(produto.getIsPromocaoAtiva());
            itensVendas.add(itemVenda);
        }
        pedido.setItens(itensVendas);
        itemVendaRepository.saveAll(itensVendas);
        transacaoService.adicionar(pedido);
        if(pedidoCreate.getOrigem().equals("Carrinho")){
            carrinhoService.esvaziarCarrinho(pedidoCreate.getIdConsumidor());
        }
        return pedido;
    }
}
