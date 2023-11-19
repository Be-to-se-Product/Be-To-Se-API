package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Estabelecimento;

import java.util.ArrayList;
import java.util.List;

public class AgendaMapper {
    public static List<Agenda> of(List<CadastroAgendaDto> cadastroAgendaDTO, Estabelecimento estabelecimento){
        List<Agenda> agendaFinal = new ArrayList<>();

        for (CadastroAgendaDto c : cadastroAgendaDTO){
            Agenda agenda = new Agenda();
            agenda.setHorarioInicio(c.getHorarioInicio());
            agenda.setHorarioFim(c.getHorarioFim());
            agenda.setDia(c.getDia());
            agenda.setEstabelecimento(estabelecimento);

            agendaFinal.add(agenda);
        }

        return agendaFinal;
    }
}
