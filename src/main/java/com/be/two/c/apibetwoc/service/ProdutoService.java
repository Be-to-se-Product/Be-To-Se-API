package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.produto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.dto.produto.ProdutoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
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
       return produtoRepository.findById(id).orElseThrow(
                ()->new NoSuchElementException("Produto não encontrado")
        );
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
    public Produto cadastrarProduto(CadastroProdutoDto cadastroProdutoDto){
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao()).get();
        Produto produto = ProdutoMapper.of(cadastroProdutoDto);
        produto.setSecao(secao);
        Produto produtoSalvo = produtoRepository.save(produto);
        for (Long tagId : cadastroProdutoDto.getTag()) {
            Tag tag = tagRepository.findById(tagId).get();
                produtoTagRepository.save(new ProdutoTag(null, tag, produtoSalvo));
        }
        return produtoSalvo;
    }
    public Produto atualizarProduto(Long id, CadastroProdutoDto cadastroProdutoDto){
        Produto produto = buscarPorId(id);
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao()).get();
        produto = ProdutoMapper.of(cadastroProdutoDto);
        produto.setSecao(secao);
        Produto produtoSalvo = produtoRepository.save(produto);
        List<ProdutoTag> tagsProduto = produtoTagRepository.buscarPorProduto(produto.getId());
        List<Long> tagEdicao = cadastroProdutoDto.getTag();

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
                    ProdutoTag novaProdutoTag = new ProdutoTag(null, tag, produtoSalvo);
                    produtoTagRepository.save(novaProdutoTag);
                }
            }
        }
        return produtoSalvo;
    }
    public void deletarProduto(Long id){
        Produto produto = buscarPorId(id);
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
    public List<Produto> barraDePesquisa(String pesquisa){return produtoRepository.buscarProdutoPorNomeOuTag(pesquisa);}
    public List<Produto> produtoEmPromocao(){
        return produtoRepository.findByIsPromocaoAtivaTrue();
    }
}
