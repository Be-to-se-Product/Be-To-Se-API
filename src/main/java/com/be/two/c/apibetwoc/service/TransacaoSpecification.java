package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Transacao;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransacaoSpecification {

    public static Specification<Transacao> entreDatas(LocalDate dataUm, LocalDate dataDois){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get("pedido").get("dataHoraPedido"), dataUm, dataDois);
    }

    public static Specification<Transacao> comStatus(String status){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("statusDescricao"), status);
    }

    public static Specification<Transacao> comMetodoPagamento(String nomeMetodoPagamento){
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("pedido").get("metodoPagamentoAceito").get("descricao"), nomeMetodoPagamento);
    }
}
