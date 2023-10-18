package com.be.two.c.apibetwoc.dto.pedido;

import com.be.two.c.apibetwoc.model.ItemVenda;

public class ItemVendaMapper {

    //Método mais pica do java
    public static ItemVenda of(ItemVendaCriacaoDto itemVendaCriacaoDto){
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setQuantidade(itemVendaCriacaoDto.quantidade());
        return itemVenda;
    }
}
