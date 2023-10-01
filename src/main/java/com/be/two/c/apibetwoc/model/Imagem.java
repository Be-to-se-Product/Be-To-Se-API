package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Imagem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeReferencia;
    private LocalDate dataCriacao;
    private String nomeImagem;
    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Estabelecimento estabelecimento;

    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private Produto produto;
}
