package com.be.two.c.apibetwoc.controller.produto.dto.mapa;


import lombok.Data;

@Data
public class EnderecoResponseDTO {
    private Double latitude;
    private Double longitude;
    private String numero;
    private String rua;
    private String bairro;
}
