package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EstabelecimentoEnderecoCadastroDTO {

    @NotBlank
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String bairro;

    @NotBlank
    private String numero;

    @NotBlank
    private String cidade;

    @NotBlank
    private String uf;


    private double geolocalizacaoX;
    private double geolocalizacaoY;
}
