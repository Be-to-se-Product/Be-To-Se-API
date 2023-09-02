package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto listarProdutoPorId(Long id){
        return produtoRepository.findById(id).get();
    }

    public Produto cadastrarProduto(CadastroProdutoDto produto){
        return produtoRepository.save(new Produto(null, produto.getNome(), produto.getDescricao(), produto.getPrecoCompra(), produto.getPrecoVenda(), produto.getUrlImagem(), produto.getCategoria(), produto.getCodigoGtin()));
    }

    public Produto atualizarProduto(Long id, CadastroProdutoDto produto){
        Produto produtoOptional = produtoRepository.findById(id).get();

        return produtoRepository.save(new Produto(produtoOptional.getId(), produto.getNome(), produto.getDescricao(), produto.getPrecoCompra(), produto.getPrecoVenda(), produto.getUrlImagem(), produto.getCategoria(), produto.getCodigoGtin()));
    }

    public void deletarProduto(Long id){
        Produto produtoOptional = produtoRepository.findById(id).get();

        produtoRepository.deleteById(id);
    }

}
