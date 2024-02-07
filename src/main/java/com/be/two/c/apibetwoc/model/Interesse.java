package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Interesse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nivelInteresse;
    @ManyToOne
    @JoinColumn(name = "fk_tag")
    private Tag tag;
    @ManyToOne
    @JoinColumn(name = "fk_consumidor")
    private Consumidor consumidor;
}
