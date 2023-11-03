package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("select distinct iv.pedido from ItemVenda iv where iv.consumidor.id = ?1")
    List<Pedido> searchByConsumidor(Long idConsumidor);

    @Query("SELECT DISTINCT p FROM Pedido p " +
            "JOIN p.itens iv " +
            "WHERE iv.produto.secao.estabelecimento.id = ?1")
    List<Pedido> searchByEstabelecimento(Long idEstabelecimento);


    @Query("SELECT DISTINCT p FROM Pedido p " +
            "JOIN p.itens iv " +
            "WHERE iv.produto.secao.estabelecimento.id = ?1 " +
            "AND p.statusDescricao = ?2")
    List<Pedido> searchByEstabelecimentoEStatus(Long idEstabelecimento, StatusPedido status);
}
