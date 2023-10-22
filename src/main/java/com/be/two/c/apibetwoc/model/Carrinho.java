package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHoraAlocacao;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "fk_produto")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "fk_consumidor")
    private Consumidor consumidor;
}
