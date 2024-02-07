package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstabelecimentoEnderecoCadastroDTO {
    private String cep;
    private String rua;
    private String bairro;
    private String numero;
    private double geolocalizacaoX;
    private double geolocalizacaoY;
}
