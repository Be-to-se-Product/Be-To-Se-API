package com.be.two.c.apibetwoc.controller.estabelecimento.mapper;


import com.be.two.c.apibetwoc.controller.estabelecimento.dto.*;
import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;

import com.be.two.c.apibetwoc.model.*;


import java.util.List;

public class EstabelecimentoMapper {
    public static Estabelecimento toEstabelecimento(CadastroEstabelecimentoDto cadastroEstabelecimentoDto, Comerciante comerciante){
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(cadastroEstabelecimentoDto.getNome());
        estabelecimento.setSegmento(cadastroEstabelecimentoDto.getSegmento());
        estabelecimento.setDataCriacao(cadastroEstabelecimentoDto.getDataCriacao());
        estabelecimento.setTelefoneContato(cadastroEstabelecimentoDto.getTelefoneContato());
        estabelecimento.setEnquadramentoJuridico(cadastroEstabelecimentoDto.getEnquadramentoJuridico());
        estabelecimento.setReferenciaInstagram(cadastroEstabelecimentoDto.getReferenciaInstagram());
        estabelecimento.setReferenciaFacebook(cadastroEstabelecimentoDto.getReferenciaFacebook());
        estabelecimento.setEmailContato(cadastroEstabelecimentoDto.getEmailContato());
        estabelecimento.setIsAtivo(true);
        estabelecimento.setComerciante(comerciante);

        return estabelecimento;
    }

    public static Estabelecimento toEstabelecimento(AtualizarEstabelecimentoDto atualizarEstabelecimentoDto, Estabelecimento estabelecimentoAntigo){
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(atualizarEstabelecimentoDto.getNome());
        estabelecimento.setSegmento(atualizarEstabelecimentoDto.getSegmento());
        estabelecimento.setDataCriacao(estabelecimentoAntigo.getDataCriacao());
        estabelecimento.setTelefoneContato(atualizarEstabelecimentoDto.getTelefoneContato());
        estabelecimento.setEnquadramentoJuridico(atualizarEstabelecimentoDto.getEnquadramentoJuridico());
        estabelecimento.setReferenciaInstagram(atualizarEstabelecimentoDto.getReferenciaInstagram());
        estabelecimento.setReferenciaFacebook(atualizarEstabelecimentoDto.getReferenciaFacebook());
        estabelecimento.setEmailContato(atualizarEstabelecimentoDto.getEmailContato());
        estabelecimento.setIsAtivo(estabelecimentoAntigo.getIsAtivo());
        estabelecimento.setComerciante(estabelecimentoAntigo.getComerciante());
        estabelecimento.setEndereco(atualizarEstabelecimentoDto.getEndereco());

        return estabelecimento;
    }


    public static Endereco of(EnderacoCricaoEstabelecimentoDto enderecoEstabelecimento){
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoEstabelecimento.getCep());
        endereco.setNumero(enderecoEstabelecimento.getNumero());
        endereco.setBairro(enderecoEstabelecimento.getBairro());
        endereco.setGeolocalizacaoX(enderecoEstabelecimento.getGeolocalizacaoX());
        endereco.setGeolocalizacaoY(enderecoEstabelecimento.getGeolocalizacaoY());
        endereco.setRua(enderecoEstabelecimento.getRua());

        return endereco;
    }

    public static ResponseEstabelecimentoDto toResponseEstabelecimento(Estabelecimento estabelecimento){

        ResponseEstabelecimentoDto responseEstabelecimentoDto = new ResponseEstabelecimentoDto();

        responseEstabelecimentoDto.setId(estabelecimento.getId());
        responseEstabelecimentoDto.setNome(estabelecimento.getNome());
        responseEstabelecimentoDto.setSegmento(estabelecimento.getSegmento());
        responseEstabelecimentoDto.setDataCriacao(estabelecimento.getDataCriacao());
        responseEstabelecimentoDto.setTelefoneContato(estabelecimento.getTelefoneContato());
        responseEstabelecimentoDto.setEnquadramentoJuridico(estabelecimento.getEnquadramentoJuridico());
        responseEstabelecimentoDto.setReferenciaInstagram(estabelecimento.getReferenciaInstagram());
        responseEstabelecimentoDto.setReferenciaFacebook(estabelecimento.getReferenciaFacebook());
        responseEstabelecimentoDto.setEmailContato(estabelecimento.getEmailContato());
        responseEstabelecimentoDto.setIsAtivo(estabelecimento.getIsAtivo());
        responseEstabelecimentoDto.setIdComerciante(estabelecimento.getComerciante().getId());
        responseEstabelecimentoDto.setCnpj(estabelecimento.getComerciante().getCnpj());
        responseEstabelecimentoDto.setEndereco(toEstabelecimentoEnderecoResponse(estabelecimento.getEndereco()));
        responseEstabelecimentoDto.setAgenda(estabelecimento.getAgenda().stream().map(EstabelecimentoMapper::toEstabelecimentoAgendaResponse).toList());
        responseEstabelecimentoDto.setMetodoPagamento(estabelecimento.getMetodoPagamentoAceito().stream().map(EstabelecimentoMapper::toEstabeleciementoMetodoPagamentoResponse).toList());
        responseEstabelecimentoDto.setSecao(estabelecimento.getSecao().stream().map(EstabelecimentoMapper::toEstabelecimentoSecaoResponse).toList());
        return responseEstabelecimentoDto;
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
