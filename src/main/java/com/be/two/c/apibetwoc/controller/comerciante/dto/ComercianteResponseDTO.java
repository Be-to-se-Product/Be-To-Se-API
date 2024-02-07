package com.be.two.c.apibetwoc.controller.comerciante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComercianteResponseDTO {
    private String cnpj;
    private String nome;
    private String razaoSocial;
    private String email;
    private ComercianteEnderecoResponseDTO endereco;


}
