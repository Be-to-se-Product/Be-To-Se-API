package com.be.two.c.apibetwoc.controller.produto.mapper;

import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.mapa.*;

import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
import com.be.two.c.apibetwoc.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@RequiredArgsConstructor
public class ProdutoMapper {

    private  static  HttpServletRequest request;
    public static Produto of(CadastroProdutoDto cadastroProdutoDto,Secao secao){
        Produto produto = new Produto();
        produto.setNome(cadastroProdutoDto.getNome());
        produto.setCodigoSku(cadastroProdutoDto.getCodigoSku());
        produto.setPreco(cadastroProdutoDto.getPreco());
        produto.setDescricao(cadastroProdutoDto.getDescricao());
        produto.setPrecoOferta(cadastroProdutoDto.getPrecoOferta());
        produto.setCodigoBarras(cadastroProdutoDto.getCodigoBarras());
        produto.setCategoria(cadastroProdutoDto.getCategoria());
        produto.setIsAtivo(true);
        produto.setSecao(secao);
        return produto;
    }

    public static ProdutoMapaResponseDTO toProdutoMapaReponse(Produto produto){

        ProdutoMapaResponseDTO produtoResponse = new ProdutoMapaResponseDTO();
        List<AvaliacaoMapaResponse> avaliacao = produto.getAvaliacoes().stream().map(element->to(element)).toList();
        produtoResponse.setId(produto.getId());
        produtoResponse.setNome(produto.getNome());
        produtoResponse.setCategoria(produto.getCategoria());
        produtoResponse.setDescricao(produto.getDescricao());
        produtoResponse.setAvaliacao(avaliacao);
        produtoResponse.setMediaAvaliacao(avaliacao.stream().mapToDouble(element->element.getQtdEstrela()).average().orElse(0));
        produtoResponse.setEstabelecimento(to(produto.getSecao().getEstabelecimento()));
        produtoResponse.setImagens(produto.getImagens().stream().map(element->element.getNomeReferencia()).toList());
        return produtoResponse;
    }


    public static AvaliacaoMapaResponse to (Avaliacao avaliacao){
        AvaliacaoMapaResponse avaliacaoMapaResponse = new AvaliacaoMapaResponse();
        avaliacaoMapaResponse.setData(avaliacao.getDataCriacao());
        avaliacaoMapaResponse.setDescricao(avaliacao.getComentario());
        avaliacaoMapaResponse.setQtdEstrela(avaliacao.getQtdEstrela());
        avaliacaoMapaResponse.setUsuario(avaliacao.getConsumidor().getNome());
        return avaliacaoMapaResponse;
    }
    public static EstabelecimentoMapaResponse to(Estabelecimento estabelecimento){
        EstabelecimentoMapaResponse estabelecimentoMapaResponse = new EstabelecimentoMapaResponse();
        List<AgendaResponseDTO> agenda = estabelecimento.getAgenda().stream().map(element->to(element)).toList();
        estabelecimentoMapaResponse.setAgenda(agenda);
        estabelecimentoMapaResponse.setNome(estabelecimento.getNome());
        estabelecimentoMapaResponse.setId(estabelecimento.getId());
        estabelecimentoMapaResponse.setSegmento(estabelecimento.getSegmento());
        estabelecimentoMapaResponse.setDataCriacao(estabelecimento.getDataCriacao());
        estabelecimentoMapaResponse.setEndereco(to(estabelecimento.getEndereco()));
        estabelecimentoMapaResponse.setTelefone(estabelecimento.getTelefoneContato());
        estabelecimentoMapaResponse.setSite(estabelecimento.getReferenciaInstagram());
        estabelecimentoMapaResponse.setMetodoPagamento(estabelecimento.getMetodoPagamentoAceito().stream().map(element->to(element.getMetodoPagamento())).toList());
        estabelecimentoMapaResponse.setTempoCarro(null);
        estabelecimentoMapaResponse.setTempoPessoa(null);
        estabelecimentoMapaResponse.setTempoBike(null);
        return estabelecimentoMapaResponse;

    }

    public static AgendaResponseDTO to(Agenda agenda){
    AgendaResponseDTO agendaResponseDTO = new AgendaResponseDTO();
    agendaResponseDTO.setDia(agenda.getDia());
    agendaResponseDTO.setHorarioInicio(agenda.getHorarioInicio());
    agendaResponseDTO.setHorarioFim(agenda.getHorarioFim());
    return agendaResponseDTO;
    }

    public static EnderecoResponseDTO to(Endereco endereco){

        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        enderecoResponseDTO.setLatitude(endereco.getGeolocalizacaoX());
        enderecoResponseDTO.setLongitude(endereco.getGeolocalizacaoY());
        enderecoResponseDTO.setNumero(endereco.getNumero());
        enderecoResponseDTO.setRua(endereco.getRua());
        enderecoResponseDTO.setBairro(endereco.getBairro());

        return enderecoResponseDTO;

    }

    public static MetodoPagamentoMapaResponse to (MetodoPagamento metodoPagamento){
        MetodoPagamentoMapaResponse metodoPagamentoMapaResponse = new MetodoPagamentoMapaResponse();
        metodoPagamentoMapaResponse.setNome(metodoPagamento.getDescricao());
        metodoPagamentoMapaResponse.setId(metodoPagamento.getId());
        return metodoPagamentoMapaResponse;
    }

    public static ProdutoDetalhamentoDto toProdutoDetalhamento (Produto produto){
        ProdutoDetalhamentoDto produtoDto = new ProdutoDetalhamentoDto();
        produtoDto.setId(produto.getId());
        produtoDto.setImagens(produto.getImagens().stream().map(element->element.getNomeReferencia()).toList());
        produtoDto.setCategoria(produto.getCategoria());
        produtoDto.setSecao(SecaoMapper.toResponse(produto.getSecao()));
        produtoDto.setNome(produto.getNome());
        produtoDto.setCodigoSku(produto.getCodigoSku());
        produtoDto.setPreco(produto.getPreco());
        produtoDto.setDescricao(produto.getDescricao());
        produtoDto.setPrecoOferta(produto.getPrecoOferta());
        produtoDto.setCodigoBarras(produto.getCodigoBarras());
        produtoDto.setIsAtivo(produto.getIsAtivo());
        produtoDto.setIsPromocaoAtiva(produto.getIsPromocaoAtiva());
        return produtoDto;
 }


}
