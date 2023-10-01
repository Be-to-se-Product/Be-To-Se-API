package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class ProdutoTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_tag")
    private Tag tag;
    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private Produto produto;
}
