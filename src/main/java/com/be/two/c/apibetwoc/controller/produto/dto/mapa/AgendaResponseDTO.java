package com.be.two.c.apibetwoc.controller.produto.dto.mapa;


import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Data
public class AgendaResponseDTO {
    private String dia;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
}
