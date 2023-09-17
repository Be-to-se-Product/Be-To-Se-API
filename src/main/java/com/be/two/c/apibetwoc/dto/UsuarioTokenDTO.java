package com.be.two.c.apibetwoc.dto;

import lombok.Data;

@Data
public class UsuarioTokenDTO {
    private Long userId;
    private String nome;
    private String email;
    private String senha;
    private String token;
}
