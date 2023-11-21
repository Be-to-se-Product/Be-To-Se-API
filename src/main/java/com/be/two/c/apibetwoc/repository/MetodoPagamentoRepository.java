package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    Optional<MetodoPagamento> findById(Long id);
}
