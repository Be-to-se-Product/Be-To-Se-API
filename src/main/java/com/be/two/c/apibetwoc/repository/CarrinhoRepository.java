package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Carrinho;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Carrinho c WHERE c.consumidor=:id")
    Integer esvaziarCarrinho(Consumidor id);
    @Query("SELECT c FROM Carrinho c WHERE c.consumidor =:id")
    List<Carrinho> carrinhoDoConsumidor(Consumidor id);
    @Query("SELECT c FROM Carrinho c WHERE c.consumidor =:consumidor AND c.produto.secao.estabelecimento =:estabelecimento")
    List<Carrinho> carrinhoDoConsumidorPorEstabelecimento(Estabelecimento estabelecimento, Consumidor consumidor);
    @Query("SELECT c FROM Carrinho c WHERE c.consumidor =:consumidor AND c.produto =:produto")
    Carrinho carrinhoDoConsumidorPorProduto(Produto produto, Consumidor consumidor);
    void deleteByIdIn(List<Long> idCarrinho);
}
