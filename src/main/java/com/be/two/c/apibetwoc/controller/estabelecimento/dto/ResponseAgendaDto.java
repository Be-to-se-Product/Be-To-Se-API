package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ResponseAgendaDto {
    private Long id;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String dia;
}
