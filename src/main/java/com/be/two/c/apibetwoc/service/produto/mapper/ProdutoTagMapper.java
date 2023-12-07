package com.be.two.c.apibetwoc.service.produto.mapper;


import com.be.two.c.apibetwoc.controller.tag.TagDTO;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.ProdutoTag;


public class ProdutoTagMapper {

    public static ProdutoTag  toProdutoTag(TagDTO tagDto, Produto produto){
        ProdutoTag produtoTag = new ProdutoTag();
        produtoTag.setProduto(produto);
        produtoTag.setTag(TagMapper.toTag(tagDto));
        return produtoTag;
    }
}
