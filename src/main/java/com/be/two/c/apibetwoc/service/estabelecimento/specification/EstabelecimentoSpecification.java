package com.be.two.c.apibetwoc.service.estabelecimento.specification;

import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.Usuario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EstabelecimentoSpecification {

    public static Specification<Estabelecimento> id(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (id == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Estabelecimento, Comerciante> joinComerciante = root.join("comerciante");
            Join<Comerciante, Usuario> joinUsuario = joinComerciante.join("usuario");
            return criteriaBuilder.equal(joinUsuario.get("id"),id);
        };
    }
    public static Specification<Estabelecimento> name(String name){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nome"), "%" + name + "%");
        };
    }
}
