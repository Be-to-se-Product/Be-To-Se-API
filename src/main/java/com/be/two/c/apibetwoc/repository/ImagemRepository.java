package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    void deleteByIdIn(List<Integer> idImagens);
}
