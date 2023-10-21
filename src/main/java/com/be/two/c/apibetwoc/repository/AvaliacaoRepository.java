package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Avaliacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Avaliacao a SET a.qtdEstrela = :novaNota, a.comentario = :novoComentario, a.dataAtualizacao = :dataEdicao  WHERE a.id = :id ")
    Avaliacao editarAvaliacao( Long id, Integer novaNota, String novoComentario, LocalDateTime dataEdicao);
    @Query("SELECT a FROM Avaliacao a WHERE a.produto.id = :id")
    List<Avaliacao>buscaPorProduto(Long id);
}
