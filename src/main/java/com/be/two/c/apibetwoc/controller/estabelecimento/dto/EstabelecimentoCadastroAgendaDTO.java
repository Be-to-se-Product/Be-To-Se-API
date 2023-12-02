package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class EstabelecimentoCadastroAgendaDTO {
    @NotNull
    private LocalTime horarioInicio;
    @NotNull
    private LocalTime horarioFim;
    @NotBlank
    private String dia;
}
