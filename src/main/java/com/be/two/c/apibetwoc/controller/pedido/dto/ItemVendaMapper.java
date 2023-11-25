package com.be.two.c.apibetwoc.controller.pedido.dto;

import com.be.two.c.apibetwoc.model.ItemVenda;

public class ItemVendaMapper {

    public static ItemVenda of(ItemVendaCriacaoDto itemVendaCriacaoDto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(itemVendaCriacaoDto.quantidade());
        return itemVenda;
    }

    public static ResponseItemVendaDto of(ItemVenda itemVenda){
        ResponseItemVendaDto responseItemVendaDto = new ResponseItemVendaDto(itemVenda.getProduto().getId(),itemVenda.getQuantidade(),itemVenda.getProduto().getNome(),itemVenda.getProduto().getPreco(),null);
        return responseItemVendaDto;
    }
}
