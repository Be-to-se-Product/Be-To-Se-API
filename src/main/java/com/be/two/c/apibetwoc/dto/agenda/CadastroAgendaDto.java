package com.be.two.c.apibetwoc.dto.agenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CadastroAgendaDto {
    @NotNull
    private LocalTime horarioInicio;
    @NotNull
    private LocalTime horarioFim;
    @NotBlank
    private String dia;
}
