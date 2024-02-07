package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nf;
    private LocalDateTime dataHoraPedido;


    @Enumerated(EnumType.STRING)
    private StatusPedido statusDescricao;
    private Boolean isPagamentoOnline;
    private LocalDateTime dataHoraRetirada;
    @ManyToOne
    @JoinColumn(name = "fk_metodo_aceito")
    private MetodoPagamentoAceito metodoPagamentoAceito;
    @OneToMany(mappedBy = "pedido")
    private List<ItemVenda> itens;
    @OneToMany(mappedBy = "pedido")
    private List<Transacao> transacoes;
}