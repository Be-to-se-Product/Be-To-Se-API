package com.be.two.c.apibetwoc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderacoCricaoEstabelecimentoDto {
    private String cep;
    private String rua;
    private String bairro;
    private Integer numero;
    private double geolocalizacaoX;
    private double geolocalizacaoY;
}
