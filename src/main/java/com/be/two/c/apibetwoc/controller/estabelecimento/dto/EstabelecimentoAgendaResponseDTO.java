package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import lombok.Data;

import java.time.LocalTime;


@Data
public class EstabelecimentoAgendaResponseDTO {

    private String dia;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

}
