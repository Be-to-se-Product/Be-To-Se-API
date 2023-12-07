package com.be.two.c.apibetwoc.controller.estabelecimento.mapper;

import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroAgendaDTO;
import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Estabelecimento;

import java.util.ArrayList;
import java.util.List;

public class AgendaMapper {
    public static List<Agenda> of(List<EstabelecimentoCadastroAgendaDTO> estabelecimentoCadastroAgendaDTO, Estabelecimento estabelecimento){
        List<Agenda> agendaFinal = new ArrayList<>();

        for (EstabelecimentoCadastroAgendaDTO c : estabelecimentoCadastroAgendaDTO){
            Agenda agenda = new Agenda();
            agenda.setHorarioInicio(c.getHorarioInicio());
            agenda.setHorarioFim(c.getHorarioFim());
            agenda.setDia(c.getDia());
            agenda.setEstabelecimento(estabelecimento);

            agendaFinal.add(agenda);
        }

        return agendaFinal;
    }

    public static Agenda toAgenda(EstabelecimentoCadastroAgendaDTO estabelecimentoCadastroAgendaDTO){
            Agenda agenda = new Agenda();
            agenda.setHorarioInicio(estabelecimentoCadastroAgendaDTO.getHorarioInicio());
            agenda.setHorarioFim(estabelecimentoCadastroAgendaDTO.getHorarioFim());
            agenda.setDia(estabelecimentoCadastroAgendaDTO.getDia());
        return agenda;
    }


}
