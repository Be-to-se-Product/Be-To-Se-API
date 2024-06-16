package com.be.two.c.apibetwoc.service.venda;

import com.be.two.c.apibetwoc.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransacaoSpecification {

    public static Specification<Transacao> comId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            criteriaQuery.distinct(true);  // Adiciona DISTINCT aqui
            return criteriaBuilder.equal(root
                    .join("pedido")
                    .join("metodoPagamentoAceito")
                    .join("estabelecimento")
                    .get("id"), id);
        };
    }

    public static Specification<Transacao> entreDatas(LocalDate dataUm, LocalDate dataDois) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (dataUm == null || dataDois == null) {
                return criteriaBuilder.conjunction();
            }
            criteriaQuery.distinct(true);  // Adiciona DISTINCT aqui
            return criteriaBuilder.between(root
                    .join("pedido")
                    .get("dataHoraPedido"), dataUm.atStartOfDay(), dataDois.atTime(23, 59, 59));
        };
    }

    public static Specification<Transacao> comStatus(StatusPedido status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);  // Adiciona DISTINCT aqui
            return criteriaBuilder.equal(root
                    .join("pedido")
                    .get("statusDescricao"), status);
        };
    }

    public static Specification<Transacao> comMetodoPagamento(String nomeMetodoPagamento) {
        return (root, query, criteriaBuilder) -> {
            if (nomeMetodoPagamento == null) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);  // Adiciona DISTINCT aqui
            return criteriaBuilder.equal(root
                    .join("pedido")
                    .join("metodoPagamentoAceito")
                    .join("metodoPagamento")
                    .get("descricao"), nomeMetodoPagamento);
        };
    }

    public static Specification<Transacao> comStatusDiferente(StatusPedido statusPedido) {
        return (root, query, criteriaBuilder) -> {
            if (statusPedido == null) {
                return criteriaBuilder.conjunction();
            }
            query.distinct(true);  // Adiciona DISTINCT aqui
            return criteriaBuilder.notEqual(root
                    .join("pedido")
                    .get("statusDescricao"), statusPedido);
        };
    }

}