package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nf;
    private LocalDateTime dataHoraPedido;
    private String statusDescricao;
    private Boolean isPagamentoOnline;
    private LocalDateTime dataHoraRetirada;
    @ManyToOne
    @JoinColumn(name = "fk_metodo_aceito")
    private MetodoPagamento metodoPagamento;
}
