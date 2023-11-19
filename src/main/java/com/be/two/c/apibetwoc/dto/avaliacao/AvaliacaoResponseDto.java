package com.be.two.c.apibetwoc.dto.avaliacao;

import com.be.two.c.apibetwoc.model.Avaliacao;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AvaliacaoResponseDto {
    private Integer qtdEstrela;
    private String comentario;

    public AvaliacaoResponseDto(Avaliacao avaliacao) {
        this.qtdEstrela = avaliacao.getQtdEstrela();
        this.comentario = avaliacao.getComentario();
    }
}
