package com.be.two.c.apibetwoc.service.produto.specification;
import com.be.two.c.apibetwoc.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;



public class ProdutoSpecification {

    public static Specification<Produto> filtrarIds(List<Integer> ids){
        return (root, criterialQuery, criteriaBuilder) -> root.get("id").in(ids);
    }

    public static Specification<Produto> isAtivo(){
        return ((root, query, criteriaBuilder) -> root.get("isAtivo").in(true));
    }
    public static Specification<Produto> name(String name){

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nome"), "%" + name + "%");
        };
    }


    public static Specification<Produto> isEstabelecimento(Long idEstabelecimento){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (idEstabelecimento == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Produto,Secao> joinSecao = root.join("secao");

            Join<Secao,Estabelecimento> joinEstabelecimento = joinSecao.join("estabelecimento");
            return  joinEstabelecimento.get("id").in(idEstabelecimento);
        };
    }
    public static Specification<Produto> metodoPagamento(String metodoPagamento){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(metodoPagamento == null){
                return criteriaBuilder.conjunction();
            }
            Join<Produto,Secao> join = root.join("secao");
            Join<Secao,Estabelecimento> joinEstabelecimento = join.join("estabelecimento");
            Join<Estabelecimento,MetodoPagamentoAceito> joinMetodoPagamento = joinEstabelecimento.join("metodoPagamentoAceito");
            Join<MetodoPagamentoAceito,MetodoPagamento> joinMetodoPagamento2 = joinMetodoPagamento.join("metodoPagamento");
            return joinMetodoPagamento2.get("descricao").in(metodoPagamento);
        };

    }

}
