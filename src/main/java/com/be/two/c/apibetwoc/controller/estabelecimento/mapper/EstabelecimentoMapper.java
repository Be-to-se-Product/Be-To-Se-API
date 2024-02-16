package com.be.two.c.apibetwoc.controller.estabelecimento.mapper;


import com.be.two.c.apibetwoc.controller.estabelecimento.dto.*;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.catalogo.EstabelecimentoCatalogoResponseDto;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.catalogo.ProdutoCatalogoResponseDto;
import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;

import com.be.two.c.apibetwoc.model.*;

import java.util.List;
import java.util.Optional;


public class EstabelecimentoMapper {
    public static Estabelecimento toEstabelecimento(EstabelecimentoCadastroDTO estabelecimentoCadastroDTO, Comerciante comerciante) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNome(estabelecimentoCadastroDTO.getNome());
        estabelecimento.setTelefoneContato(estabelecimentoCadastroDTO.getTelefone());
        estabelecimento.setReferenciaInstagram(estabelecimentoCadastroDTO.getInstagram());
        estabelecimento.setReferenciaFacebook(estabelecimentoCadastroDTO.getFacebook());
        estabelecimento.setEmailContato(estabelecimentoCadastroDTO.getEmail());
        estabelecimento.setIsAtivo(true);
        estabelecimento.setComerciante(comerciante);

        return estabelecimento;
    }

    public static Estabelecimento toEstabelecimento(EstabelecimentoAtualizarDTO estabelecimentoAtualizarDTO, Estabelecimento estabelecimento) {

        estabelecimento.setNome(estabelecimentoAtualizarDTO.getNome());
        estabelecimento.setSegmento(estabelecimentoAtualizarDTO.getSegmento());
        estabelecimento.setTelefoneContato(estabelecimentoAtualizarDTO.getTelefoneContato());
        estabelecimento.setReferenciaInstagram(estabelecimentoAtualizarDTO.getReferenciaInstagram());
        estabelecimento.setReferenciaFacebook(estabelecimentoAtualizarDTO.getReferenciaFacebook());
        estabelecimento.setEmailContato(estabelecimentoAtualizarDTO.getEmailContato());
        return estabelecimento;
    }


    public static Endereco of(EstabelecimentoEnderecoCadastroDTO enderecoEstabelecimento) {
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoEstabelecimento.getCep());
        endereco.setNumero(enderecoEstabelecimento.getNumero());
        endereco.setBairro(enderecoEstabelecimento.getBairro());
        endereco.setGeolocalizacaoX(enderecoEstabelecimento.getGeolocalizacaoX());
        endereco.setGeolocalizacaoY(enderecoEstabelecimento.getGeolocalizacaoY());
        endereco.setRua(enderecoEstabelecimento.getLogradouro());

        return endereco;
    }

    public static EstabelecimentoResponseDTO toResponseEstabelecimento(Estabelecimento estabelecimento) {

        EstabelecimentoResponseDTO estabelecimentoResponseDTO = new EstabelecimentoResponseDTO();
        if (estabelecimento.getImagens() != null) {
            estabelecimentoResponseDTO.setImagens(estabelecimento.getImagens().stream().map(Imagem::getNomeReferencia).toList());
        }
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
        if (estabelecimento.getMetodoPagamentoAceito() != null) {
            estabelecimentoResponseDTO.setMetodoPagamento(estabelecimento.getMetodoPagamentoAceito().stream().map(EstabelecimentoMapper::toEstabeleciementoMetodoPagamentoResponse).toList());
        }
        estabelecimentoResponseDTO.setSecao(estabelecimento.getSecao().stream().map(EstabelecimentoMapper::toEstabelecimentoSecaoResponse).toList());
        return estabelecimentoResponseDTO;
    }

    public static EstabelecimentoComercianteResponseDTO toResponseEstabelecimentoComerciante(Estabelecimento estabelecimento, Integer qtdPedidoPendentes, Integer qtdProdutosAtivos) {

        EstabelecimentoComercianteResponseDTO estabelecimentoResponseDTO = new EstabelecimentoComercianteResponseDTO();
        if (estabelecimento.getImagens() != null) {
            estabelecimentoResponseDTO.setImagens(estabelecimento.getImagens().stream().map(Imagem::getNomeReferencia).toList());
        }
        estabelecimentoResponseDTO.setId(estabelecimento.getId());
        estabelecimentoResponseDTO.setNome(estabelecimento.getNome());
        estabelecimentoResponseDTO.setSegmento(estabelecimento.getSegmento());
        estabelecimentoResponseDTO.setProdutosAtivos(10);
        estabelecimentoResponseDTO.setPedidosPendentes(10);
        return estabelecimentoResponseDTO;
    }

    private static EstabelecentoSecaoResponseDTO toEstabelecimentoSecaoResponse(Secao secao) {
        EstabelecentoSecaoResponseDTO estabelecentoSecaoResponseDTO = new EstabelecentoSecaoResponseDTO();
        estabelecentoSecaoResponseDTO.setId(secao.getId());
        estabelecentoSecaoResponseDTO.setNome(secao.getDescricao());
        return estabelecentoSecaoResponseDTO;
    }

    public static EstabelecimentoMetodoPagamentoResponseDTO toEstabeleciementoMetodoPagamentoResponse(MetodoPagamentoAceito metodoPagamentoAceito) {
        EstabelecimentoMetodoPagamentoResponseDTO estabelecimentoMetodoPagamentoResponse = new EstabelecimentoMetodoPagamentoResponseDTO();
        estabelecimentoMetodoPagamentoResponse.setId(metodoPagamentoAceito.getMetodoPagamento().getId());
        estabelecimentoMetodoPagamentoResponse.setNome(metodoPagamentoAceito.getMetodoPagamento().getDescricao());
        return estabelecimentoMetodoPagamentoResponse;
    }

    public static EstabelecimentoAgendaResponseDTO toEstabelecimentoAgendaResponse(Agenda agenda) {
        EstabelecimentoAgendaResponseDTO estabelecimentoAgendaResponse = new EstabelecimentoAgendaResponseDTO();
        estabelecimentoAgendaResponse.setDia(agenda.getDia());
        estabelecimentoAgendaResponse.setHorarioInicio(agenda.getHorarioInicio());
        estabelecimentoAgendaResponse.setHorarioFim(agenda.getHorarioFim());
        return estabelecimentoAgendaResponse;
    }

    public static EstabelecimentoEnderecoResponseDTO toEstabelecimentoEnderecoResponse(Endereco endereco) {
        EstabelecimentoEnderecoResponseDTO estabelecimentoEnderecoResponse = new EstabelecimentoEnderecoResponseDTO();
        estabelecimentoEnderecoResponse.setNumero(endereco.getNumero());
        estabelecimentoEnderecoResponse.setCep(endereco.getCep());
        estabelecimentoEnderecoResponse.setBairro(endereco.getBairro());
        estabelecimentoEnderecoResponse.setRua(endereco.getRua());
        return estabelecimentoEnderecoResponse;
    }

    public static Secao toSecao(EstabelecimentoSecaoAtualizarDTO secao, Estabelecimento estabelecimento) {
        Secao secaoEntity = new Secao();
        Optional<Long> optIdSecao = Optional.ofNullable(secao.getId());

        if (optIdSecao.isPresent()) {
            secaoEntity.setId(optIdSecao.get());
        }
        secaoEntity.setEstabelecimento(estabelecimento);
        secaoEntity.setDescricao(secao.getNome());
        return secaoEntity;
    }

    public static EstabelecimentoCatalogoResponseDto toCatalogoDto(Estabelecimento estabelecimento) {
        EstabelecimentoCatalogoResponseDto dto = new EstabelecimentoCatalogoResponseDto();
        dto.setId(estabelecimento.getId());
        dto.setNome(estabelecimento.getNome());
        dto.setSegmento(estabelecimento.getSegmento());
        dto.setDataCriacao(estabelecimento.getDataCriacao());
        dto.setTelefoneContato(estabelecimento.getTelefoneContato());
        dto.setReferenciaInstagram(estabelecimento.getReferenciaInstagram());
        dto.setReferenciaFacebook(estabelecimento.getReferenciaFacebook());
        dto.setEmailContato(estabelecimento.getEmailContato());
        if (estabelecimento.getImagens() != null) {
            dto.setImagens(estabelecimento.getImagens().stream().map(Imagem::getNomeReferencia).toList());
        }

        List<Produto> produtos = estabelecimento.getSecao().stream().
                map(Secao::getProduto).
                flatMap(List::stream).
                toList();
        dto.setProdutos(produtos.
                stream().
                map(EstabelecimentoMapper::toCatalogoDto).
                toList());
        return dto;
    }

    public static ProdutoCatalogoResponseDto toCatalogoDto(Produto produto) {
        ProdutoCatalogoResponseDto produtoCatalogoResponseDto = new ProdutoCatalogoResponseDto();
        produtoCatalogoResponseDto.setId(produto.getId());
        produtoCatalogoResponseDto.setNome(produto.getNome());
        produtoCatalogoResponseDto.setCodigoSku(produto.getCodigoSku());
        produtoCatalogoResponseDto.setPreco(produto.getPreco());
        produtoCatalogoResponseDto.setDescricao(produto.getDescricao());
        produtoCatalogoResponseDto.setPrecoOferta(produto.getPrecoOferta());
        produtoCatalogoResponseDto.setCodigoBarras(produto.getCodigoBarras());
        produtoCatalogoResponseDto.setCategoria(produto.getCategoria());
        produtoCatalogoResponseDto.setIsPromocaoAtiva(produto.getIsPromocaoAtiva());
        if (produto.getImagens() != null) {
            produtoCatalogoResponseDto.setImagens(produto.
                    getImagens().
                    stream().
                    map(Imagem::getNomeReferencia).
                    toList());
        }

        return produtoCatalogoResponseDto;
    }
}
