package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.avaliacao.AvaliacaoDTO;
import com.be.two.c.apibetwoc.dto.avaliacao.AvaliacaoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Avaliacao;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.AvaliacaoRepository;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private ConsumidorRepository consumidorRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    public Avaliacao publicar(AvaliacaoDTO avaliacaoDTO){
        Consumidor consumidor = consumidorRepository.findById(avaliacaoDTO.getConsumidor()).orElseThrow(
                ()->new EntidadeNaoExisteException("Consumidor não encontrado")
        );
        Produto produto = buscarProdutoPorId(avaliacaoDTO.getProduto());
        Avaliacao avaliacao = AvaliacaoMapper.of(avaliacaoDTO);
        LocalDate dataCriacao= LocalDate.now();
        LocalDateTime dataAtualizacao = LocalDateTime.now();
        avaliacao.setProduto(produto);
        avaliacao.setDataCriacao(dataCriacao);
        avaliacao.setDataAtualizacao(dataAtualizacao);
        avaliacao.setConsumidor(consumidor);

        return avaliacaoRepository.save(avaliacao);
    }
    public List<Avaliacao> buscarAvaliacaoPorProduto(Long id){
        Produto produto = buscarProdutoPorId(id);
        return avaliacaoRepository.buscaPorProduto(id);
    }
    private Avaliacao buscarPorId(Long id){
        Avaliacao avaliacao = avaliacaoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Avaliação não encontrada")
        );
        return avaliacao;
    }
    private Produto buscarProdutoPorId(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Produto não encontrado")
        );
        return produto;
    }
    public Avaliacao editar(String cometario, Integer qtdEstrela, Long id){
        Avaliacao avaliacao = buscarPorId(id);
        LocalDateTime dataAtualizacao = LocalDateTime.now();
        avaliacao.setQtdEstrela(qtdEstrela);
        avaliacao.setComentario(cometario);
        avaliacao.setDataAtualizacao(dataAtualizacao);
        Avaliacao avaliacaoEditada=avaliacaoRepository.save(avaliacao);
        return avaliacaoEditada;
    }
    public void excluir(Long id){
        buscarPorId(id);
        avaliacaoRepository.deleteById(id);
    }
}
