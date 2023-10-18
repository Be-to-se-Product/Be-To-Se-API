package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "fk_consumidor")
    private Consumidor consumidor;
    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private Produto produto;

    private boolean isPromocaoAtiva;
}
