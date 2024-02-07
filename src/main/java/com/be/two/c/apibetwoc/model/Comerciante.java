package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Comerciante {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cnpj;
    private String nome;
    private LocalDate dataCriacao;
    private LocalDate dataUltimoAcesso;
    private String razaoSocial;


    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;


    @OneToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;
    private Boolean isAtivo;

    @OneToMany(mappedBy = "comerciante")
    private List<Estabelecimento> estabelecimento;
}
