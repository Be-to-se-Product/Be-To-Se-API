package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoVendaDto;
import com.be.two.c.apibetwoc.model.ItemVenda;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.repository.ItemVendaRepository;
import com.be.two.c.apibetwoc.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final ItemVendaRepository itemVendaRepository;
    public Transacao adicionar(Pedido pedido){
        Double total = 0.0;
        List<ItemVenda> itens = pedido.getItens();
        List<Long> ids = pedido.getItens().stream().map(ItemVenda::getId).collect(Collectors.toList());
        List<ItemVenda> itensVenda = itemVendaRepository.findByIdIn(ids);
        for (ItemVenda i : itensVenda){
            total += i.getProduto().getPreco();
        }
        Transacao transacao = new Transacao();
        transacao.setPedido(pedido);
        transacao.setValor(total);
        transacao.setEstornado(false);
        transacao.setDataTransacao(LocalDate.now());
        return transacaoRepository.save(transacao);
    }
}
