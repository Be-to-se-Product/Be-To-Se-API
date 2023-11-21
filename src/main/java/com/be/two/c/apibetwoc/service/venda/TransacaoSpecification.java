package com.be.two.c.apibetwoc.service.venda;

import com.be.two.c.apibetwoc.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransacaoSpecification {

    public static Specification<Transacao> entreDatas(LocalDate dataUm, LocalDate dataDois) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (dataUm == null || dataDois == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder
                    .between(root
                            .get("dataTransacao"), dataUm, dataDois);
        };

    }

    public static Specification<Transacao> comStatus(String status) {
        if (status == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        StatusPedido statusPedido = StatusPedido.valueOf(status.toUpperCase());
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder
                        .equal(root
                                .join("pedido")
                                .get("statusDescricao"), statusPedido);
    }

    public static Specification<Transacao> comMetodoPagamento(String nomeMetodoPagamento) {
        return (root, query, criteriaBuilder) -> {
            if (nomeMetodoPagamento == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Transacao, Pedido> pedidoJoin = root.join("pedido");
            Join<Pedido, MetodoPagamentoAceito> pedidoMetodoPagamentoAceitoJoin = pedidoJoin.join("metodoPagamentoAceito");
            Join<MetodoPagamentoAceito, MetodoPagamento> metodoPagamentoAceitoMetodoPagamentoJoin = pedidoMetodoPagamentoAceitoJoin.join("metodoPagamento");
            return criteriaBuilder.equal(metodoPagamentoAceitoMetodoPagamentoJoin.get("descricao"), nomeMetodoPagamento);

        };
    }
}
