package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("select distinct iv.pedido from ItemVenda iv where iv.consumidor.id = :idConsumidor")
    List<Pedido> searchByConsumidor(Long idConsumidor);

    @Query("SELECT DISTINCT p FROM Pedido p " +
            "JOIN p.itens iv " +
            "WHERE iv.produto.secao.estabelecimento.id = :idEstabelecimento ")
    List<Pedido> searchByEstabelecimento(Long idEstabelecimento);


    @Query("SELECT DISTINCT p FROM Pedido p " +
            "JOIN p.itens iv " +
            "WHERE iv.produto.secao.estabelecimento.id = :idEstabelecimento " +
            "AND p.statusDescricao = :status")
    List<Pedido> searchByEstabelecimentoEStatus(Long idEstabelecimento, StatusPedido status);


    @Query("SELECT DISTINCT p FROM Pedido p " +
            "JOIN p.itens iv " +
            "WHERE iv.consumidor.id = :id " +
            "AND p.statusDescricao = :status ")
    List<Pedido> searchByConsumidorEStatus(Long id, StatusPedido status);
}
