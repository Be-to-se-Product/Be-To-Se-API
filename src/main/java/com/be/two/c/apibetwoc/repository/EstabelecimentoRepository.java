package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    Estabelecimento findById(long id);
    List<Estabelecimento> findBySegmento(String segmento);
}
