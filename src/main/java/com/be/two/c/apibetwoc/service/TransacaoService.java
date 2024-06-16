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
    public void adicionar(Pedido pedido){
        Double total = 0.0;
        List<Long> ids = pedido.getItens().stream().map(ItemVenda::getId).collect(Collectors.toList());
        List<ItemVenda> itensVenda = itemVendaRepository.findByIdIn(ids);
        for (ItemVenda i : itensVenda){
            total += i.getProduto().getPreco() * i.getQuantidade();
        }
        Transacao transacao = new Transacao();
        transacao.setPedido(pedido);
        transacao.setValor(total);
        transacao.setEstornado(false);
        transacao.setTaxa(total * 0.15);
        transacao.setDataTransacao(LocalDate.now());
        transacaoRepository.save(transacao);
    }
}
