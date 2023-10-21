package com.be.two.c.apibetwoc.dto.avaliacao;

import com.be.two.c.apibetwoc.model.Avaliacao;

public class AvaliacaoMapper {
    public static Avaliacao of(AvaliacaoDTO avaliacaoDTO){
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setQtdEstrela(avaliacaoDTO.getQtdEstrela());
        avaliacao.setComentario(avaliacaoDTO.getComentario());
        return avaliacao;
    }
}
