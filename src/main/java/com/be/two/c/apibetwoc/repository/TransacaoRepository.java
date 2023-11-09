package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
    Page<Transacao> findAll(Specification<Transacao> spec, Pageable pageable);
}
