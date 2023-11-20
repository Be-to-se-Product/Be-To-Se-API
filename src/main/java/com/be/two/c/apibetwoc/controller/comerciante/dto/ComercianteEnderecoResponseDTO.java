package com.be.two.c.apibetwoc.controller.comerciante.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComercianteEnderecoResponseDTO {
    private String rua;
    private String numero;
    private String bairro;
    private String cep;


}
