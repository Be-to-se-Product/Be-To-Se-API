package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Time;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Agenda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Time horarioInicio;
    private Time horarioFim;
    private String dia;
    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Estabelecimento estabelecimento;
}
