package com.be.two.c.apibetwoc.controller.avaliacao.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AvaliacaoRequestDTO {
    @DecimalMin(value ="1")
    @DecimalMax(value = "5")
    private Integer qtdEstrela;
    @NotBlank
    private String comentario;
    @NotNull
    private Long consumidor;
    @NotNull
    private Long produto;
}
