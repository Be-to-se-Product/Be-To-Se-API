package com.be.two.c.apibetwoc.dto;

import com.be.two.c.apibetwoc.model.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCriacaoDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, max = 40)
        String senha,

        TipoUsuario tipoUsuario
) {
}
