package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Carrinho;
import com.be.two.c.apibetwoc.model.Consumidor;
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
    @Query("UPDATE Carrinho c SET c.quantidade = :quantidade, c.dataHoraAlocacao = :dtH WHERE c.id = :id")
    Integer editarCarrinho(Long id, LocalDateTime dtH, Integer quantidade);
    @Modifying
    @Transactional
    @Query("DELETE FROM Carrinho c WHERE c.consumidor=:id")
    Integer esvaziarCarrinho(Consumidor id);
    @Query("SELECT c FROM Carrinho c WHERE c.consumidor =:id")
    List<Carrinho> carrinhoDoConsumidor(Consumidor id);
    @Query("INSERT INTO Carrinho c(c.quantidade, c.produto, c.consumidor, c.dataHoraAlocacao)VALUES(:quantidade, :produto, :consumidor, :dtH)")
    Carrinho cadastrarCarrinho(Integer quantidade, Produto produto, Consumidor consumidor, LocalDateTime dtH);

}
