package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Secao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Estabelecimento estabelecimento;

    @OneToMany
    @JoinColumn(name = "fk_secao")
    private List<Produto> produto;
}
