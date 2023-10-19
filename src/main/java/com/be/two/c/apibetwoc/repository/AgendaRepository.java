package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByEstabelecimentoId(Long id);
}
