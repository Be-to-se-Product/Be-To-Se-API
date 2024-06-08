package com.be.two.c.apibetwoc.controller.pedido.dto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoEstabelecimentoResponseDTO;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.model.ItemVenda;

public class ItemVendaMapper {

    public static ItemVenda of(ItemVendaCriacaoDto itemVendaCriacaoDto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(itemVendaCriacaoDto.quantidade());
        return itemVenda;
    }
    public static ItemVenda of(ItemVendaCreateDto dto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(dto.getQuantidade());
        return itemVenda;
    }
    public static ResponseItemVendaDto of(ItemVenda itemVenda){
        return new ResponseItemVendaDto(
                itemVenda.getProduto().getId(),
                itemVenda.getQuantidade(),
                ProdutoMapper.toProdutoDetalhamento(itemVenda.getProduto()));
    }
}
