package com.be.two.c.apibetwoc.service.produto.specification;

import com.be.two.c.apibetwoc.model.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;



public class ProdutoSpecification {

    public static Specification<Produto> filtrarIds(List<Integer> ids){
        return (root, criterialQuery, criteriaBuilder) -> {
            Predicate idPredicate = root.get("id").in(ids);
            Predicate statusPredicate = criteriaBuilder.equal(root.get("isAtivo"), true);
            return criteriaBuilder.and(idPredicate, statusPredicate);
        };
    }
    public static Specification<Produto> name(String name){

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nome"), "%" + name + "%");
        };
    }


    public static Specification<Produto> isAtivoByEstabelecimento(Long id){
        return  (root, criteriaQuery, criteriaBuilder) -> {
            Join<Produto,Secao> join = root.join("secao");
            Join<Secao,Estabelecimento> joinEstabelecimento = join.join("estabelecimento");
           return  criteriaBuilder.and(
                   criteriaBuilder.equal(root.get("isAtivo"), true),
                   joinEstabelecimento.get("id").in(id)
           );
        };
    }
    public static Specification<Produto> metodoPagamento(Long metodoPagamento){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(metodoPagamento == null){
                return criteriaBuilder.conjunction();
            }
            Join<Produto,Secao> join = root.join("secao");
            Join<Secao,Estabelecimento> joinEstabelecimento = join.join("estabelecimento");
            Join<Estabelecimento,MetodoPagamentoAceito> joinMetodoPagamento = joinEstabelecimento.join("metodoPagamentoAceito");
            Join<MetodoPagamentoAceito,MetodoPagamento> joinMetodoPagamento2 = joinMetodoPagamento.join("metodoPagamento");
            return joinMetodoPagamento2.get("id").in(metodoPagamento);
        };

    }

}
