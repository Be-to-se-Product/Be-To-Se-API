package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    public Produto buscarPorId(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Produto não encontrado")
        );
        return produto;
    }
    public List<Produto> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();
        int n = produtos.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (produtos.get(j).getNome().compareTo(produtos.get(j + 1).getNome()) > 0) {
                    Produto temp = produtos.get(j);
                    produtos.set(j, produtos.get(j + 1));
                    produtos.set(j + 1, temp);
                }
            }
        }
        return produtos;
    }
    public Produto listarProdutoPorId(Long id){
        Produto produto = buscarPorId(id);
        return produtoRepository.findById(id).get();
    }
    public Produto cadastrarProduto(CadastroProdutoDto produto){
        Secao secao = secaoRepository.findById(produto.getSecao()).get();
        Produto novoProduto =produtoRepository.save(new Produto(null,produto.getNome(),produto.getCodigoSku(),produto.getPreco(),produto.getDescricao(),produto.getPrecoOferta(),produto.getCodigoBarras(),produto.getCategoria(),produto.getIsAtivo(),produto.getIsPromocaoAtiva(),secao));
        for (Long tagId : produto.getTag()) {
            Tag tag = tagRepository.findById(tagId).get();
                produtoTagRepository.save(new ProdutoTag(null, tag, novoProduto));
        }
        return novoProduto;
    }
    public Produto atualizarProduto(Long id, CadastroProdutoDto produto){
        Produto produtoOptional = buscarPorId(id);
        Secao secao = secaoRepository.findById(produto.getSecao()).get();
        //List<Tag> tag = tagRepository.findById(produto.getTag());
        Produto produtoEditado =produtoRepository.save(new Produto(produtoOptional.getId(),produto.getNome(),produto.getCodigoSku(),produto.getPreco(),produto.getDescricao(),produto.getPrecoOferta(),produto.getCodigoBarras(),produto.getCategoria(),produto.getIsAtivo(),produto.getIsPromocaoAtiva(),secao));
        List<ProdutoTag> tagsProduto = produtoTagRepository.buscarPorProduto(produtoOptional.getId());
        List<Long> tagEdicao = produto.getTag();

        for (ProdutoTag produtoTag : tagsProduto) {
            boolean encontrada = false;
            for (Long tagIdEdicao : tagEdicao) {
                if (produtoTag.getTag().getId().equals(tagIdEdicao)) {
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                produtoTagRepository.delete(produtoTag);
            }
        }

        for (Long tagIdEdicao : tagEdicao) {
            boolean tagJaAssociada = false;

            for (ProdutoTag produtoTag : tagsProduto) {
                if (produtoTag.getTag().getId().equals(tagIdEdicao)) {
                    tagJaAssociada = true;
                    break;
                }
            }

            if (!tagJaAssociada) {
                Tag tag = tagRepository.findById(tagIdEdicao).orElse(null);

                if (tag != null) {
                    ProdutoTag novaProdutoTag = new ProdutoTag(null, tag, produtoEditado);
                    produtoTagRepository.save(novaProdutoTag);
                }
            }
        }
        return produtoEditado;
    }
    public void deletarProduto(Long id){
        Produto produtoOptional = buscarPorId(id);
        List<ProdutoTag> tags = produtoTagRepository.buscarPorProduto(id);
        List<Long> produtoTagIds = tags.stream().map(ProdutoTag::getId).collect(Collectors.toList());
        for(Long tagDeletar : produtoTagIds){
            produtoTagRepository.deleteById(tagDeletar);
        }
        produtoRepository.deleteById(id);
    }
    public void statusProduto(boolean status, Long id){
        Produto produto = buscarPorId(id);
        produtoRepository.statusProduto(status, id);
    }
    public void statusPromocaoProduto(Long id, boolean status){
        Produto produto = buscarPorId(id);
        produtoRepository.statusPromocao(status, id);
    }
    public List<Produto> produtoPorEstabelecimento(Long id){
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(
                ()-> new EntidadeNaoExisteException("Estabelecimento não encontrado")
        );
        return produtoRepository.buscaProdutosPorLoja(id);
    }
    public List<Produto> barraDePesquisa(String pesquisa){
        return produtoRepository.buscarProdutoPorNomeOuTag(pesquisa);
    }
    public List<Produto> produtoEmPromocao(){
        return produtoRepository.findByIsPromocaoAtivaTrue();
    }
}
