package com.be.two.c.apibetwoc.controller.avaliacao.mapper;

import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoRequestDTO;
import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoResponseDTO;
import com.be.two.c.apibetwoc.model.Avaliacao;

public class AvaliacaoMapper {
    public static Avaliacao toAvaliacaoRequest(AvaliacaoRequestDTO avaliacaoDTO){
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setQtdEstrela(avaliacaoDTO.getQtdEstrela());
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        return avaliacao;
    }
    public static AvaliacaoResponseDTO toAvaliacaoResponseDTO(Avaliacao avaliacao){
        AvaliacaoResponseDTO avaliacaoResponseDTO = new AvaliacaoResponseDTO();
        avaliacaoResponseDTO.setQtdEstrela(avaliacao.getQtdEstrela());
        avaliacaoResponseDTO.setComentario(avaliacao.getComentario());
        avaliacaoResponseDTO.setUsuario(avaliacao.getConsumidor().getNome());
        avaliacaoResponseDTO.setData(avaliacao.getDataCriacao());
        if(avaliacao.getConsumidor().getImagem() != null) {
            avaliacaoResponseDTO.setImagem(avaliacao.getConsumidor().getImagem().getNomeReferencia());
        }
        return avaliacaoResponseDTO;
    }
}
