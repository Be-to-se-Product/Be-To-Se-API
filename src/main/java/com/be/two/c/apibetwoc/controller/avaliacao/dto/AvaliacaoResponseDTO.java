package com.be.two.c.apibetwoc.controller.avaliacao.dto;

import com.be.two.c.apibetwoc.model.Avaliacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponseDTO {
    private Integer qtdEstrela;
    private String comentario;
    private String usuario;
    private String imagem;
    private LocalDate data;
}
