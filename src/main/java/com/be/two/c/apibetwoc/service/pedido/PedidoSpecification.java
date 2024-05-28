package com.be.two.c.apibetwoc.service.pedido;

import com.be.two.c.apibetwoc.model.ItemVenda;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> byConsumidor(Long idConsumidor) {
        return (root, query, criteriaBuilder) -> {
            if (idConsumidor == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Pedido, ItemVenda> join = root.join("itens");
            return criteriaBuilder.equal(join.get("consumidor").get("id"), idConsumidor);
        };
    }


    public static Specification<Pedido> byStatus(StatusPedido status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("statusDescricao"), status);
        };
    }
}
