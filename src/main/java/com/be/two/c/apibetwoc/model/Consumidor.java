package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Consumidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String celular;
    private String genero;
    private LocalDate dataNascimento;
    private LocalDateTime dataCriacao;
    private Boolean isAtivo = true;
    private LocalDate dataUltimaCompra;

    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "fk_imagem")
    private Imagem imagem;
}
