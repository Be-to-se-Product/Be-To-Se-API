package com.be.two.c.apibetwoc.controller.estabelecimento.mapper;


import com.be.two.c.apibetwoc.controller.estabelecimento.dto.*;
import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;

import com.be.two.c.apibetwoc.model.*;

public class EstabelecimentoMapper {
    public static Estabelecimento toEstabelecimento(EstabelecimentoCadastroDTO estabelecimentoCadastroDTO, Comerciante comerciante){
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(estabelecimentoCadastroDTO.getNome());
        estabelecimento.setSegmento(estabelecimentoCadastroDTO.getSegmento());
        estabelecimento.setDataCriacao(estabelecimentoCadastroDTO.getDataCriacao());
        estabelecimento.setTelefoneContato(estabelecimentoCadastroDTO.getTelefoneContato());
        estabelecimento.setEnquadramentoJuridico(estabelecimentoCadastroDTO.getEnquadramentoJuridico());
        estabelecimento.setReferenciaInstagram(estabelecimentoCadastroDTO.getReferenciaInstagram());
        estabelecimento.setReferenciaFacebook(estabelecimentoCadastroDTO.getReferenciaFacebook());
        estabelecimento.setEmailContato(estabelecimentoCadastroDTO.getEmailContato());
        estabelecimento.setIsAtivo(true);
        estabelecimento.setComerciante(comerciante);

        return estabelecimento;
    }

    public static Estabelecimento toEstabelecimento(EstabelecimentoAtualizarDTO estabelecimentoAtualizarDTO, Estabelecimento estabelecimentoAntigo){
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(estabelecimentoAtualizarDTO.getNome());
        estabelecimento.setSegmento(estabelecimentoAtualizarDTO.getSegmento());
        estabelecimento.setDataCriacao(estabelecimentoAntigo.getDataCriacao());
        estabelecimento.setTelefoneContato(estabelecimentoAtualizarDTO.getTelefoneContato());
        estabelecimento.setEnquadramentoJuridico(estabelecimentoAtualizarDTO.getEnquadramentoJuridico());
        estabelecimento.setReferenciaInstagram(estabelecimentoAtualizarDTO.getReferenciaInstagram());
        estabelecimento.setReferenciaFacebook(estabelecimentoAtualizarDTO.getReferenciaFacebook());
        estabelecimento.setEmailContato(estabelecimentoAtualizarDTO.getEmailContato());
        estabelecimento.setIsAtivo(estabelecimentoAntigo.getIsAtivo());
        estabelecimento.setComerciante(estabelecimentoAntigo.getComerciante());
        estabelecimento.setEndereco(estabelecimentoAtualizarDTO.getEndereco());

        return estabelecimento;
    }


    public static Endereco of(EstabelecimentoEnderecoCadastroDTO enderecoEstabelecimento){
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoEstabelecimento.getCep());
        endereco.setNumero(enderecoEstabelecimento.getNumero());
        endereco.setBairro(enderecoEstabelecimento.getBairro());
        endereco.setGeolocalizacaoX(enderecoEstabelecimento.getGeolocalizacaoX());
        endereco.setGeolocalizacaoY(enderecoEstabelecimento.getGeolocalizacaoY());
        endereco.setRua(enderecoEstabelecimento.getRua());

        return endereco;
    }

    public static EstabelecimentoResponseDTO toResponseEstabelecimento(Estabelecimento estabelecimento){

        EstabelecimentoResponseDTO estabelecimentoResponseDTO = new EstabelecimentoResponseDTO();

        estabelecimentoResponseDTO.setId(estabelecimento.getId());
        estabelecimentoResponseDTO.setNome(estabelecimento.getNome());
        estabelecimentoResponseDTO.setSegmento(estabelecimento.getSegmento());
        estabelecimentoResponseDTO.setTelefoneContato(estabelecimento.getTelefoneContato());

        estabelecimentoResponseDTO.setReferenciaInstagram(estabelecimento.getReferenciaInstagram());
        estabelecimentoResponseDTO.setReferenciaFacebook(estabelecimento.getReferenciaFacebook());
        estabelecimentoResponseDTO.setEmailContato(estabelecimento.getEmailContato());
        estabelecimentoResponseDTO.setIdComerciante(estabelecimento.getComerciante().getId());
        estabelecimentoResponseDTO.setCnpj(estabelecimento.getComerciante().getCnpj());
        estabelecimentoResponseDTO.setEndereco(toEstabelecimentoEnderecoResponse(estabelecimento.getEndereco()));
        estabelecimentoResponseDTO.setAgenda(estabelecimento.getAgenda().stream().map(EstabelecimentoMapper::toEstabelecimentoAgendaResponse).toList());
        estabelecimentoResponseDTO.setMetodoPagamento(estabelecimento.getMetodoPagamentoAceito().stream().map(EstabelecimentoMapper::toEstabeleciementoMetodoPagamentoResponse).toList());
        estabelecimentoResponseDTO.setSecao(estabelecimento.getSecao().stream().map(EstabelecimentoMapper::toEstabelecimentoSecaoResponse).toList());
        return estabelecimentoResponseDTO;
    }

    private static EstabelecentoSecaoResponseDTO toEstabelecimentoSecaoResponse(Secao secao) {
    EstabelecentoSecaoResponseDTO estabelecentoSecaoResponseDTO = new EstabelecentoSecaoResponseDTO();
    estabelecentoSecaoResponseDTO.setId(secao.getId());
    estabelecentoSecaoResponseDTO.setNome(secao.getDescricao());
    return estabelecentoSecaoResponseDTO;
    }

    public static EstabelecimentoMetodoPagamentoResponseDTO toEstabeleciementoMetodoPagamentoResponse(MetodoPagamentoAceito metodoPagamentoAceito){
        EstabelecimentoMetodoPagamentoResponseDTO estabelecimentoMetodoPagamentoResponse = new EstabelecimentoMetodoPagamentoResponseDTO();
        estabelecimentoMetodoPagamentoResponse.setId(metodoPagamentoAceito.getMetodoPagamento().getId());
        estabelecimentoMetodoPagamentoResponse.setNome(metodoPagamentoAceito.getMetodoPagamento().getDescricao());
        return estabelecimentoMetodoPagamentoResponse;
    }

    public static EstabelecimentoAgendaResponseDTO toEstabelecimentoAgendaResponse(Agenda agenda){
        EstabelecimentoAgendaResponseDTO estabelecimentoAgendaResponse = new EstabelecimentoAgendaResponseDTO();
        estabelecimentoAgendaResponse.setDia(agenda.getDia());
        estabelecimentoAgendaResponse.setHorarioInicio(agenda.getHorarioInicio());
        estabelecimentoAgendaResponse.setHorarioFim(agenda.getHorarioFim());
        return estabelecimentoAgendaResponse;
    }

    public static EstabelecimentoEnderecoResponseDTO toEstabelecimentoEnderecoResponse( Endereco endereco){
        EstabelecimentoEnderecoResponseDTO estabelecimentoEnderecoResponse = new EstabelecimentoEnderecoResponseDTO();
        estabelecimentoEnderecoResponse.setNumero(endereco.getNumero());
        estabelecimentoEnderecoResponse.setBairro(endereco.getBairro());
        estabelecimentoEnderecoResponse.setRua(endereco.getRua());
        return estabelecimentoEnderecoResponse;
    }
}
