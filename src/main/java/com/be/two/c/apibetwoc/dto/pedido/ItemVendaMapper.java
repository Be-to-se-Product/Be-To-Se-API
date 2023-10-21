package com.be.two.c.apibetwoc.dto.pedido;

import com.be.two.c.apibetwoc.model.ItemVenda;

public class ItemVendaMapper {

    public static ItemVenda of(ItemVendaCriacaoDto itemVendaCriacaoDto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(itemVendaCriacaoDto.quantidade());
        return itemVenda;
    }

    public static ResponseItemVendaDto of(ItemVenda itemVenda){
        ResponseItemVendaDto responseItemVendaDto = new ResponseItemVendaDto();
        responseItemVendaDto.setId(itemVenda.getId());
        responseItemVendaDto.setQuantidade(itemVenda.getQuantidade());
        responseItemVendaDto.setProdutoNome(itemVenda.getProduto().getNome());
        return responseItemVendaDto;
    }
}
