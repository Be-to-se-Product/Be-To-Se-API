package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findBySecaoEstabelecimentoId(Long idEstabelemento);
    @Query("SELECT p FROM Produto p WHERE p.secao.estabelecimento.id = :estabelecimentoId")
    List<Produto> buscaProdutosPorLoja(Long estabelecimentoId);
    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.isPromocaoAtiva = :status WHERE p.id = :id")
    Integer statusPromocao(boolean status, Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.isAtivo = :status WHERE p.id = :id")
    Integer statusProduto(boolean status, Long id);
    @Query("SELECT DISTINCT p FROM ProdutoTag pt JOIN pt.tag t JOIN pt.produto p WHERE LOWER(t.descricao) LIKE LOWER(CONCAT('%', :pesquisa, '%')) OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :pesquisa, '%'))")
    List<Produto> buscarProdutoPorNomeOuTag(String pesquisa);
    List<Produto> findByIsPromocaoAtivaTrue();
}
