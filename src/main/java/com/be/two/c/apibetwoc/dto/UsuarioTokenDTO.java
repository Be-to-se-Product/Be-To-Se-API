package com.be.two.c.apibetwoc.dto;

import com.be.two.c.apibetwoc.model.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioTokenDTO {
    private Long userId;
    private String email;
    private String token;
    private TipoUsuario tipoUsuario;
}
