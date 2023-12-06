package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class MetodoPagamentoAceito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Estabelecimento estabelecimento;

    @ManyToOne
    @JoinColumn(name = "fk_metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    private Boolean isAtivo;

    @OneToMany(mappedBy = "metodoPagamentoAceito")
    List<Pedido> pedidos;
}