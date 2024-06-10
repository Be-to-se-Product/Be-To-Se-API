package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String rua;
    private String bairro;
    private String numero;
    private String logradouro;
    private double geolocalizacaoX;
    private double geolocalizacaoY;

    @OneToMany(mappedBy = "endereco")
    private List<Estabelecimento> estabelecimento;

}
