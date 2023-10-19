package com.be.two.c.apibetwoc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Double precoOferta;
    private String categoria;
    private String codigoSku;
    private boolean isAtivo;
    private boolean isPromocaoAtiva;
    private Integer qtdVendas;
    @ManyToOne
    @JoinColumn(name = "fk_secao")
    private Secao secao;
    @JsonIgnore
    @OneToMany
    private List<Imagem> imagens;
}
