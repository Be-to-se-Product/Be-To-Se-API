package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findBySecaoEstabelecimentoId(Long idEstabelemento);
}
