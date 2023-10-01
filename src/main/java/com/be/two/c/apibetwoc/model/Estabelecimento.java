package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class Estabelecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String segmento;
    private LocalDate dataCriacao;
    private String telefoneContato;
    private String enquadramentoJuridico;
    private String referenciaInstagram;
    private String referenciaFacebook;
    private String emailContato;
    private Boolean isAtivo;
    @ManyToOne
    @JoinColumn(name = "fk_comerciante")
    private Comerciante comerciante;
    @ManyToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;
}
