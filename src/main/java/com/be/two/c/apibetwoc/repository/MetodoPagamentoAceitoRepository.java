package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoPagamentoAceitoRepository extends JpaRepository<MetodoPagamentoAceito, Long> {
    List<MetodoPagamentoAceito> findByEstabelecimentoId(Long id);
    List<MetodoPagamentoAceito> findByEstabelecimento(Estabelecimento estabelecimento);
}
