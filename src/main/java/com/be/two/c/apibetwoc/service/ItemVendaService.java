package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.pedido.ItemVendaCriacaoDto;
import com.be.two.c.apibetwoc.dto.pedido.ItemVendaMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.ItemVenda;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.repository.ItemVendaRepository;
import com.be.two.c.apibetwoc.repository.PedidoRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
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

    public Pedido cadastrarItensVenda(List<ItemVendaCriacaoDto> itensVenda) {
        List<ItemVenda> itensSalvos = new ArrayList<>();
        Pedido pedido = pedidoService.cadastrar(itensVenda.get(0).pedido());
        for (ItemVendaCriacaoDto i : itensVenda) {
            ItemVenda itemVenda = ItemVendaMapper.of(i);
            Consumidor consumidor = consumidorRepository.findById(i.idConsumidor())
                    .orElseThrow(() -> new EntidadeNaoExisteException("Consumidor informado não existe"));
            Produto produto = produtoRepository.findById(i.idProduto())
                    .orElseThrow(() -> new EntidadeNaoExisteException("Produto informado não existe"));
            itemVenda.setConsumidor(consumidor);
            itemVenda.setProduto(produto);
            itemVenda.setPedido(pedido);
            itemVenda.setPromocaoAtiva(produto.getIsPromocaoAtiva());
            itensSalvos.add(itemVenda);
            itemVendaRepository.save(itemVenda);
        }
//        itemVendaRepository.saveAll(itensSalvos);
        return pedido;
    }
}
