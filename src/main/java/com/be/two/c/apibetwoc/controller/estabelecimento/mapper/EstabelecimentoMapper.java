package com.be.two.c.apibetwoc.controller.estabelecimento.mapper;


import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EnderacoCricaoEstabelecimentoDto;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.ResponseEstabelecimentoDto;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.AtualizarEstabelecimentoDto;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.CadastroEstabelecimentoDto;
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

    public static ResponseEstabelecimentoDto toResponseEstabelecimento(Estabelecimento estabelecimento, List<Agenda> agenda, List<MetodoPagamentoAceito> metodos){

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
        responseEstabelecimentoDto.setEndereco(estabelecimento.getEndereco());
        responseEstabelecimentoDto.setAgenda(agenda);
        responseEstabelecimentoDto.setMetodoPagamento(metodos);

        return responseEstabelecimentoDto;
    }
}
