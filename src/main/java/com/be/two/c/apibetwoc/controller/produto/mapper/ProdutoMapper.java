package com.be.two.c.apibetwoc.controller.produto.mapper;

import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;

import com.be.two.c.apibetwoc.controller.produto.dto.mapa.*;

import com.be.two.c.apibetwoc.model.*;

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
        produto.setIsAtivo(true);
        return produto;
    }

    public static ProdutoMapaResponseDTO to(Produto produto){

        ProdutoMapaResponseDTO produtoResponse = new ProdutoMapaResponseDTO();
        List<AvaliacaoMapaResponse> avaliacao = produto.getAvaliacoes().stream().map(element->to(element)).toList();
        produtoResponse.setId(produto.getId());
        produtoResponse.setNome(produto.getNome());
        produtoResponse.setCategoria(produto.getCategoria());
        produtoResponse.setDescricao(produto.getDescricao());
        produtoResponse.setAvaliacao(avaliacao);
        produtoResponse.setMediaAvaliacao(avaliacao.stream().mapToDouble(element->element.getQtdEstrela()).average().orElse(0));
        produtoResponse.setEstabelecimento(to(produto.getSecao().getEstabelecimento()));
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
}
