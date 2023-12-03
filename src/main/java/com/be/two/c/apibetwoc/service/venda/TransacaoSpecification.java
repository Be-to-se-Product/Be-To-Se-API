package com.be.two.c.apibetwoc.service.venda;

import com.be.two.c.apibetwoc.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransacaoSpecification {

    public static Specification<Transacao> comId(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.
                    join("pedido")
                    .join("metodoPagamentoAceito")
                    .join("estabelecimento")
                    .get("id"), id);
        };
    }

    public static Specification<Transacao> entreDatas(LocalDateTime dataUm, LocalDateTime dataDois) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (dataUm == null || dataDois == null) {
                return criteriaBuilder.conjunction();
            }
            System.out.println(dataUm);
            System.out.println(dataDois);
            return criteriaBuilder
                    .between(root
                            .join("pedido")
                            .get("dataHoraPedido"), dataUm, dataDois);
        };

    }

    public static Specification<Transacao> comStatus(StatusPedido status) {
        if (status == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        System.out.println(status);
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder
                        .equal(root
                                .join("pedido")
                                .get("statusDescricao"), status);
    }

    public static Specification<Transacao> comMetodoPagamento(String nomeMetodoPagamento) {
        return (root, query, criteriaBuilder) -> {
            if (nomeMetodoPagamento == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root
                    .join("pedido")
                    .join("metodoPagamentoAceito")
                    .join("metodoPagamento")
                    .get("descricao"), nomeMetodoPagamento);

        };
    }

    public static Specification<Transacao> comStatusDiferente(StatusPedido statusPedido){
        return (root, query, criteriaBuilder) -> {
            if (statusPedido == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.notEqual(root
                    .join("pedido")
                    .get("statusDescricao"), statusPedido);

        };
    }
}
