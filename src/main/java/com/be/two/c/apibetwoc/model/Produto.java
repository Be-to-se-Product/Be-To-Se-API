package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double precoCompra;
    private Double precoVenda;
    private String urlImagem;
    private String categoria;
    private String codigoGtin;
}
