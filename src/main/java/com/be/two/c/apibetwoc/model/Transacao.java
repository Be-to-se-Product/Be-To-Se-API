package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Transacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valor;
    private Double taxa;
    private boolean isEstornado;
    private LocalDate dataTransacao;
    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private Pedido pedido;
}
