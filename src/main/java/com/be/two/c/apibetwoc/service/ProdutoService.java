package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.ProdutoTag;
import com.be.two.c.apibetwoc.model.Secao;
import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.be.two.c.apibetwoc.repository.ProdutoTagRepository;
import com.be.two.c.apibetwoc.repository.SecaoRepository;
import com.be.two.c.apibetwoc.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private SecaoRepository secaoRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ProdutoTagRepository produtoTagRepository;
    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto listarProdutoPorId(Long id){
        return produtoRepository.findById(id).get();
    }

    public Produto cadastrarProduto(CadastroProdutoDto produto){
        Secao secao = secaoRepository.findById(produto.getSecao()).get();
        Tag tag = tagRepository.findById(produto.getTag()).get();
        Produto novoProduto =produtoRepository.save(new Produto(null,produto.getNome(),produto.getCodigoSku(),produto.getPreco(),produto.getDescricao(),produto.getPrecoOferta(),produto.getCodigoBarras(),produto.getCategoria(),produto.isAtivo(),produto.isPromocaoAtiva(),secao));
        //produtoTagRepository.save(new ProdutoTag(null,tag,novoProduto));
        return novoProduto;
    }

    public Produto atualizarProduto(Long id, CadastroProdutoDto produto){
        Produto produtoOptional = produtoRepository.findById(id).get();
        Secao secao = secaoRepository.findById(produto.getSecao()).get();
        Tag tag = tagRepository.findById(produto.getTag()).get();
        Produto produtoEditado =produtoRepository.save(new Produto(produtoOptional.getId(),produto.getNome(),produto.getCodigoSku(),produto.getPreco(),produto.getDescricao(),produto.getPrecoOferta(),produto.getCodigoBarras(),produto.getCategoria(),produto.isAtivo(),produto.isPromocaoAtiva(),secao));

        return produtoEditado;
    }

    public void deletarProduto(Long id){
        Produto produtoOptional = produtoRepository.findById(id).get();

        produtoRepository.deleteById(id);
    }

}
