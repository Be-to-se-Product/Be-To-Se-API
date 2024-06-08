package com.be.two.c.apibetwoc.controller.produto.mapper;

import com.be.two.c.apibetwoc.controller.produto.dto.*;

import com.be.two.c.apibetwoc.controller.produto.dto.mapa.*;

import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.service.Bicicleta;
import com.be.two.c.apibetwoc.service.Carro;
import com.be.two.c.apibetwoc.service.ITempoPercurso;
import com.be.two.c.apibetwoc.service.Pessoa;
import com.be.two.c.apibetwoc.service.produto.mapper.ProdutoTagMapper;
import com.be.two.c.apibetwoc.service.tag.mapper.TagMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@RequiredArgsConstructor
public class ProdutoMapper {

    private static HttpServletRequest request;

    public static Produto toProduto(CadastroProdutoDto cadastroProdutoDto, Secao secao, Long id) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(cadastroProdutoDto.getNome());
        produto.setCodigoSku(cadastroProdutoDto.getCodigoSku());
        produto.setPreco(cadastroProdutoDto.getPreco());
        produto.setDescricao(cadastroProdutoDto.getDescricao());
        produto.setPrecoOferta(cadastroProdutoDto.getPrecoOferta());
        produto.setCodigoBarras(cadastroProdutoDto.getCodigoBarras());
        produto.setCategoria(cadastroProdutoDto.getCategoria());
        produto.setIsAtivo(true);
        produto.setTags(List.of());
        produto.setSecao(secao);
        return produto;
    }

    public static Produto toProduto(CadastroProdutoDto cadastroProdutoDto, Secao secao) {
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
        produto.setImagens(new ArrayList<>());
        return produto;
    }

    public static TempoPercurssoDTO toTempoPercurso(Estabelecimento estabelecimento, Double x, Double y) {
        TempoPercurssoDTO tempoPercursso = new TempoPercurssoDTO();
        List<ITempoPercurso> calculo = List.of(
                new Carro(),
                new Pessoa(),
                new Bicicleta()
        );
        tempoPercursso.setCarro(calculo.get(0).calcularTempoDeslocamento(x, y, estabelecimento.getEndereco().getGeolocalizacaoX(), estabelecimento.getEndereco().getGeolocalizacaoY()));
        tempoPercursso.setPessoa(calculo.get(0).calcularTempoDeslocamento(x, y, estabelecimento.getEndereco().getGeolocalizacaoX(), estabelecimento.getEndereco().getGeolocalizacaoY()));
        tempoPercursso.setBicicleta(calculo.get(0).calcularTempoDeslocamento(x, y, estabelecimento.getEndereco().getGeolocalizacaoX(), estabelecimento.getEndereco().getGeolocalizacaoY()));
        return tempoPercursso;
    }


    public static ProdutoMapaResponseDTO toProdutoMobile(Produto produto,Double latitude,Double longitude){
        ProdutoMapaResponseDTO produtoResponse = new ProdutoMapaResponseDTO();
        List<AvaliacaoMapaResponse> avaliacao = produto.getAvaliacoes().stream().map(ProdutoMapper::toAvaliacaoResponse).toList();
        produtoResponse.setId(produto.getId());
        produtoResponse.setNome(produto.getNome());
        produtoResponse.setCategoria(produto.getCategoria());
        produtoResponse.setDescricao(produto.getDescricao());
        produtoResponse.setAvaliacao(avaliacao);
        produtoResponse.setPrecoAntigo(produto.getPreco());
        produtoResponse.setPrecoAtual(produto.getPreco());
        if(produto.getPrecoOferta()!=null) {
            produtoResponse.setPrecoAtual(produto.getPrecoOferta());
        }
        produtoResponse.setMediaAvaliacao(avaliacao.stream().mapToDouble(AvaliacaoMapaResponse::getQtdEstrela).average().orElse(0));
        Estabelecimento estabelecimento = produto.getSecao().getEstabelecimento();
        produtoResponse.setEstabelecimento(toEstabelecimentoResponse(estabelecimento,latitude,longitude));
        produtoResponse.setImagens(produto.getImagens().stream().map(Imagem::getNomeReferencia).toList());
        return produtoResponse;
    }

    public static ProdutoMapaResponseDTO toProdutoMapaReponse(Produto produto, Double x, Double y) {
        ProdutoMapaResponseDTO produtoResponse = new ProdutoMapaResponseDTO();
        List<AvaliacaoMapaResponse> avaliacao = produto.getAvaliacoes().stream().map(ProdutoMapper::toAvaliacaoResponse).toList();
        produtoResponse.setId(produto.getId());
        produtoResponse.setNome(produto.getNome());
        produtoResponse.setCategoria(produto.getCategoria());
        produtoResponse.setDescricao(produto.getDescricao());
        produtoResponse.setAvaliacao(avaliacao);

        produtoResponse.setPrecoAntigo(produto.getPreco());

        produtoResponse.setPrecoAtual(produto.getPreco());
        if(produto.getPrecoOferta()!=null) {
            produtoResponse.setPrecoAtual(produto.getPrecoOferta());
        }
        produtoResponse.setMediaAvaliacao(avaliacao.stream().mapToDouble(AvaliacaoMapaResponse::getQtdEstrela).average().orElse(0));
        if(produto.getSecao() != null) produtoResponse.setEstabelecimento(toEstabelecimentoResponse(produto.getSecao().getEstabelecimento(),x,y));
        produtoResponse.setImagens(produto.getImagens().stream().map(Imagem::getNomeReferencia).toList());

        return produtoResponse;
    }


    public static AvaliacaoMapaResponse toAvaliacaoResponse(Avaliacao avaliacao) {
        AvaliacaoMapaResponse avaliacaoMapaResponse = new AvaliacaoMapaResponse();
        avaliacaoMapaResponse.setData(avaliacao.getDataCriacao());
        avaliacaoMapaResponse.setDescricao(avaliacao.getComentario());
        if(avaliacao.getQtdEstrela()==null){
            avaliacaoMapaResponse.setQtdEstrela(0);
        }
        else{
            avaliacaoMapaResponse.setQtdEstrela(avaliacao.getQtdEstrela());
        }

        avaliacaoMapaResponse.setUsuario(avaliacao.getConsumidor().getNome());
        return avaliacaoMapaResponse;
    }

    public static EstabelecimentoMapaResponse toEstabelecimentoResponse(Estabelecimento estabelecimento, Double x, Double y) {
        List<ITempoPercurso> tempoPercursos = List.of(
                new Carro(),
                new Pessoa(),
                new Bicicleta());

        EstabelecimentoMapaResponse estabelecimentoMapaResponse = new EstabelecimentoMapaResponse();
        List<AgendaResponseDTO> agenda = estabelecimento.getAgenda().stream().map(ProdutoMapper::toAgendaResponse).toList();
        estabelecimentoMapaResponse.setAgenda(agenda);
        estabelecimentoMapaResponse.setNome(estabelecimento.getNome());
        estabelecimentoMapaResponse.setId(estabelecimento.getId());
        estabelecimentoMapaResponse.setSegmento(estabelecimento.getSegmento());
        estabelecimentoMapaResponse.setDataCriacao(estabelecimento.getDataCriacao());
        estabelecimentoMapaResponse.setEndereco(toEnderecoResponse(estabelecimento.getEndereco()));
        estabelecimentoMapaResponse.setTelefone(estabelecimento.getTelefoneContato());
        estabelecimentoMapaResponse.setSite(estabelecimento.getReferenciaInstagram());
        estabelecimentoMapaResponse.setMetodoPagamento(estabelecimento.getMetodoPagamentoAceito().stream().map(element -> toMetodoPagamentoResponse(element.getMetodoPagamento())).toList());
        List<Imagem> imagens = estabelecimento.getImagens();
        if(!imagens.isEmpty()){
            estabelecimentoMapaResponse.setImagem(imagens.get(0).getNomeReferencia());
        }

        if(x==null || y==null){
            return estabelecimentoMapaResponse;
        }
        estabelecimentoMapaResponse.setTempoCarro(tempoPercursos.get(0).calcularTempoDeslocamento(estabelecimento.getEndereco().getGeolocalizacaoX(), estabelecimento.getEndereco().getGeolocalizacaoY(), x, y));
        estabelecimentoMapaResponse.setTempoPessoa(tempoPercursos.get(1).calcularTempoDeslocamento(estabelecimento.getEndereco().getGeolocalizacaoX(), estabelecimento.getEndereco().getGeolocalizacaoY(), x, y));
        estabelecimentoMapaResponse.setTempoBike(tempoPercursos.get(2).calcularTempoDeslocamento(estabelecimento.getEndereco().getGeolocalizacaoX(), estabelecimento.getEndereco().getGeolocalizacaoY(), x, y));
        return estabelecimentoMapaResponse;

    }

    public static AgendaResponseDTO toAgendaResponse(Agenda agenda) {
        AgendaResponseDTO agendaResponseDTO = new AgendaResponseDTO();
        agendaResponseDTO.setDia(agenda.getDia());
        agendaResponseDTO.setHorarioInicio(agenda.getHorarioInicio());
        agendaResponseDTO.setHorarioFim(agenda.getHorarioFim());
        return agendaResponseDTO;
    }

    public static EnderecoResponseDTO toEnderecoResponse(Endereco endereco) {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        enderecoResponseDTO.setLatitude(endereco.getGeolocalizacaoX());
        enderecoResponseDTO.setLongitude(endereco.getGeolocalizacaoY());
        enderecoResponseDTO.setNumero(endereco.getNumero());
        enderecoResponseDTO.setRua(endereco.getRua());
        enderecoResponseDTO.setBairro(endereco.getBairro());
        return enderecoResponseDTO;

    }

    public static MetodoPagamentoMapaResponse toMetodoPagamentoResponse(MetodoPagamento metodoPagamento) {
        MetodoPagamentoMapaResponse metodoPagamentoMapaResponse = new MetodoPagamentoMapaResponse();
        metodoPagamentoMapaResponse.setNome(metodoPagamento.getDescricao());
        metodoPagamentoMapaResponse.setId(metodoPagamento.getId());
        return metodoPagamentoMapaResponse;
    }

    public static ProdutoDetalhamentoDto toProdutoDetalhamento(Produto produto) {
        ProdutoDetalhamentoDto produtoDto = new ProdutoDetalhamentoDto();
        produtoDto.setId(produto.getId());
        if (produto.getImagens() != null) {
            produtoDto.setImagens(produto.getImagens().stream().map(Imagem::getNomeReferencia).toList());
        }
        produtoDto.setCategoria(produto.getCategoria());
        if (produto.getSecao() != null) {
            produtoDto.setSecao(toProdutoSecaoResponse(produto.getSecao()));
        }
        produtoDto.setNome(produto.getNome());
        produtoDto.setCodigoSku(produto.getCodigoSku());
        produtoDto.setPreco(produto.getPreco());
        produtoDto.setDescricao(produto.getDescricao());
        produtoDto.setPrecoOferta(produto.getPrecoOferta());
        produtoDto.setCodigoBarras(produto.getCodigoBarras());
        produtoDto.setIsAtivo(produto.getIsAtivo());
        produtoDto.setIsPromocaoAtiva(produto.getIsPromocaoAtiva());
        if (produto.getTags() != null) {
            List<Tag> tags = produto.getTags().stream().map(ProdutoTag::getTag).toList();
            produtoDto.setTags(tags.stream().map(TagMapper::toTagResponse).toList());
        }
        produtoDto.setEstabelecimento(toProdutoEstabeleciementoResponse(produto.getSecao().getEstabelecimento()));

        return produtoDto;
    }


    public static Produto toProduto(Produto produto, Secao secao, ProdutoAtualizarDTO produtoAtualizacao, Long id) {
        produto.setNome(produtoAtualizacao.getNome());
        produto.setCodigoSku(produtoAtualizacao.getCodigoSku());
        produto.setPreco(produtoAtualizacao.getPreco());
        produto.setDescricao(produtoAtualizacao.getDescricao());
        produto.setPrecoOferta(produto.getPrecoOferta());
        produto.setPreco(produto.getPreco());
        produto.setCodigoBarras(produto.getCodigoBarras());
        produto.setCategoria(produto.getCategoria());
        produto.setIsPromocaoAtiva(produto.getIsPromocaoAtiva());
        produto.setSecao(secao);
        return produto;
    }

    public static ProdutoSecaoResponseDTO toProdutoSecaoResponse(Secao secao) {
        ProdutoSecaoResponseDTO secaoDetalhamentoDto = new ProdutoSecaoResponseDTO();
        if(secao !=null) {
            secaoDetalhamentoDto.setId(secao.getId());
            secaoDetalhamentoDto.setDescricao(secao.getDescricao());
            secaoDetalhamentoDto.setEstabelecimento(toProdutoEstabeleciementoResponse(secao.getEstabelecimento()));
        }
        return secaoDetalhamentoDto;
    }


    public static ProdutoEstabelecimentoResponseDTO toProdutoEstabeleciementoResponse(Estabelecimento estabelecimento) {
        ProdutoEstabelecimentoResponseDTO estabelecimentoResponse = new ProdutoEstabelecimentoResponseDTO();
        estabelecimentoResponse.setId(estabelecimento.getId());
        estabelecimentoResponse.setNome(estabelecimento.getNome());
        estabelecimentoResponse.setIdMetodo(estabelecimento.getMetodoPagamentoAceito().stream().map(MetodoPagamentoAceito::getId).toList());
        if(estabelecimento.getImagens().stream().findFirst().isPresent()){
            estabelecimentoResponse.setImagem(estabelecimento.getImagens().stream().map(Imagem::getNomeReferencia).toList().stream().findFirst().get());
        }
        return estabelecimentoResponse;
    }
    public static ProdutoVendaResponseDto toprodutoVendaResponse(ProdutoVendaDto dto){
        ProdutoVendaResponseDto produto = new ProdutoVendaResponseDto();
        produto.setId(dto.getIdProduto());
        produto.setImagem(produto.getImagem());
        produto.setQtd(dto.getQuantidade());

        return produto;
    }


}
