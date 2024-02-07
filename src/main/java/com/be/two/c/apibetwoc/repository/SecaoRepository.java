package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Secao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecaoRepository extends JpaRepository<Secao, Long> {
    Secao findByDescricao(String descricao);
    List<Secao> findByEstabelecimentoId(Long id);
}
