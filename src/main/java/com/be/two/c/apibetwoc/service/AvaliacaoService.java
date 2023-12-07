package com.be.two.c.apibetwoc.service;


import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoRequestDTO;
import com.be.two.c.apibetwoc.controller.avaliacao.mapper.AvaliacaoMapper;
import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoResponseDTO;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Avaliacao;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.AvaliacaoRepository;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final ConsumidorRepository consumidorRepository;

    private final ProdutoRepository produtoRepository;

    private final ImagemService imagemService;

    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository usuarioRepository;

    public Avaliacao publicar(AvaliacaoRequestDTO avaliacaoRequestDTO){
        Usuario usuario = usuarioRepository.findById(autenticacaoService.loadUsuarioDetails().getId()).orElseThrow(EntityNotFoundException::new);

        if(usuario.getConsumidor()==null){
            throw new EntityNotFoundException();
        }

        Produto produto = buscarProdutoPorId(avaliacaoRequestDTO.getProduto());
        Avaliacao avaliacao = AvaliacaoMapper.toAvaliacaoRequest(avaliacaoRequestDTO);
        LocalDate dataCriacao= LocalDate.now();
        LocalDateTime dataAtualizacao = LocalDateTime.now();
        avaliacao.setProduto(produto);
        avaliacao.setDataCriacao(dataCriacao);
        avaliacao.setDataAtualizacao(dataAtualizacao);
        avaliacao.setConsumidor(usuario.getConsumidor());

        return avaliacaoRepository.save(avaliacao);
    }
    public List<Avaliacao> buscarAvaliacaoPorProduto(Long id){
        Produto produto = buscarProdutoPorId(id);
        List<Avaliacao> avalicoes = produto.getAvaliacoes();
        return avalicoes;
    }
    private Avaliacao buscarPorId(Long id){
        Avaliacao avaliacao = avaliacaoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Avaliação não encontrada")
        );
        Consumidor consumidor = avaliacao.getConsumidor();

        if(consumidor.getImagem()!=null) {
            consumidor.setImagem(imagemService.formatterImagensURI(consumidor.getImagem()));
        }
        avaliacao.setConsumidor(consumidor);
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
