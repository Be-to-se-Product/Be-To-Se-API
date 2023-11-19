package com.be.two.c.apibetwoc.controller.avaliacao.mapper;

import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoRequestDTO;
import com.be.two.c.apibetwoc.model.Avaliacao;

public class AvaliacaoMapper {
    public static Avaliacao toAvaliacaoRequest(AvaliacaoRequestDTO avaliacaoDTO){
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setQtdEstrela(avaliacaoDTO.getQtdEstrela());
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        return avaliacao;
    }
}
