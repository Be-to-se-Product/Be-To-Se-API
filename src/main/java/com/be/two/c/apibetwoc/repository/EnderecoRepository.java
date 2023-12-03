package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    void deleteByEstabelecimentoId(Long id);
}
