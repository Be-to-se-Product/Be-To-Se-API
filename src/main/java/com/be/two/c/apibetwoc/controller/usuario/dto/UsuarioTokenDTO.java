package com.be.two.c.apibetwoc.controller.usuario.dto;

import com.be.two.c.apibetwoc.model.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioTokenDTO {
    private Long id;
    private String email;
    private String token;
    private TipoUsuario tipoUsuario;
}
