package com.be.two.c.apibetwoc.dto.produto;

import com.be.two.c.apibetwoc.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

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
        produto.setIsAtivo(cadastroProdutoDto.getIsAtivo());
        produto.setIsPromocaoAtiva(cadastroProdutoDto.getIsPromocaoAtiva());
        return produto;
    }
}
