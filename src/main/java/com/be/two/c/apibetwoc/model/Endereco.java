package com.be.two.c.apibetwoc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of="id")
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String rua;
    private String bairro;
    private Integer numero;
    private double geolocalizacaoX;
    private double geolocalizacaoY;

}
