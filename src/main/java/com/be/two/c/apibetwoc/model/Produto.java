package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String codigoSku;
    private Double preco;
    private String descricao;
    private Double precoOferta;
    private String codigoBarras;
    private String categoria;
    private Boolean isAtivo;
    private Boolean isPromocaoAtiva;
    private Integer qtdVendido;
    @ManyToOne
    @JoinColumn(name = "fk_secao")
    private Secao secao;

    @OneToMany
    @JoinColumn(name = "fk_estabelecimento")
    private List<MetodoPagamentoAceito> metodoPagamentoAceito;

    @OneToMany(mappedBy = "produto")
    private List<ProdutoTag> tags;

    @OneToMany(mappedBy = "produto")
    private List<Imagem> imagens;

    @OneToMany(mappedBy = "produto")
    private List<Avaliacao> avaliacoes;

}
