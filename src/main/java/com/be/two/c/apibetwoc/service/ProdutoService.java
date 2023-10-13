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
    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto listarProdutoPorId(Long id){
        return produtoRepository.findById(id).get();
    }

    public Produto cadastrarProduto(CadastroProdutoDto produto){
        Secao secao = secaoRepository.findById(produto.getSecao()).get();
        Produto novoProduto =produtoRepository.save(new Produto(null,produto.getNome(),produto.getCodigoSku(),produto.getPreco(),produto.getDescricao(),produto.getPrecoOferta(),produto.getCodigoBarras(),produto.getCategoria(),produto.isAtivo(),produto.isPromocaoAtiva(),secao));
        for (Long tagId : produto.getTag()) {
            Tag tag = tagRepository.findById(tagId).get();
                produtoTagRepository.save(new ProdutoTag(null, tag, novoProduto));
        }
        return novoProduto;
    }

    public Produto atualizarProduto(Long id, CadastroProdutoDto produto){
        Produto produtoOptional = produtoRepository.findById(id).get();
        Secao secao = secaoRepository.findById(produto.getSecao()).get();
        //List<Tag> tag = tagRepository.findById(produto.getTag());
        Produto produtoEditado =produtoRepository.save(new Produto(produtoOptional.getId(),produto.getNome(),produto.getCodigoSku(),produto.getPreco(),produto.getDescricao(),produto.getPrecoOferta(),produto.getCodigoBarras(),produto.getCategoria(),produto.isAtivo(),produto.isPromocaoAtiva(),secao));
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
        Produto produtoOptional = produtoRepository.findById(id).get();
        List<ProdutoTag> tags = produtoTagRepository.buscarPorProduto(id);
        List<Long> produtoTagIds = tags.stream().map(ProdutoTag::getId).collect(Collectors.toList());
        for(Long tagDeletar : produtoTagIds){
            produtoTagRepository.deleteById(tagDeletar);
        }
        produtoRepository.deleteById(id);
    }

}
