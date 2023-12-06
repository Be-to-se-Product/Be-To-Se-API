package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of="id")
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String rua;
    private String bairro;
    private String numero;
    private double geolocalizacaoX;
    private double geolocalizacaoY;

    @OneToMany(mappedBy = "endereco")
    private List<Estabelecimento> estabelecimento;

}
