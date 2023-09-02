package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
