package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public List<Agenda> listarPorEstabelecimentoId(Long id){
        return agendaRepository.findByEstabelecimentoId(id);
    }
}
