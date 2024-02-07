package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class    Avaliacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer qtdEstrela;
    private String comentario;
    private LocalDate dataCriacao;
    private LocalDateTime dataAtualizacao;
    @ManyToOne
    @JoinColumn(name = "fk_consumidor")
    private Consumidor consumidor;
    @ManyToOne
    @JoinColumn(name = "fk_produto")
    Produto produto;
}
