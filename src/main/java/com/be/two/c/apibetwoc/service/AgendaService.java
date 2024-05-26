package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroAgendaDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.AgendaMapper;
import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public List<Agenda> cadastrarAgenda(List<EstabelecimentoCadastroAgendaDTO> agendaDTO, Estabelecimento estabelecimento){
        List<Agenda> agendas = agendaDTO.stream().map(AgendaMapper::toAgenda).toList();
        agendas.forEach(a -> a.setEstabelecimento(estabelecimento));
        return agendaRepository.saveAll(agendas);
    }
    public List<Agenda> listarPorEstabelecimentoId(Long id){
        return agendaRepository.findByEstabelecimentoId(id);
    }
}
