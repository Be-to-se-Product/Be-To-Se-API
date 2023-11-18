package com.be.two.c.apibetwoc.dto.produto;

import com.be.two.c.apibetwoc.model.Produto;

public class ProdutoMapper {
    public static Produto of(CadastroProdutoDto cadastroProdutoDto){
        Produto produto = new Produto();

        produto.setNome(cadastroProdutoDto.getNome());
        produto.setCodigoSku(cadastroProdutoDto.getCodigoSku());
        produto.setPreco(cadastroProdutoDto.getPreco());
        produto.setDescricao(cadastroProdutoDto.getDescricao());
        produto.setPrecoOferta(cadastroProdutoDto.getPrecoOferta());
        produto.setCodigoBarras(cadastroProdutoDto.getCodigoBarras());
        produto.setCategoria(cadastroProdutoDto.getCategoria());
        produto.setIsAtivo(true);

        return produto;
    }
}
