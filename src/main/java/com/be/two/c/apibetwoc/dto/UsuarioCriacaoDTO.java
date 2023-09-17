package com.be.two.c.apibetwoc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCriacaoDTO(
        @NotBlank
        @Size(min = 2, max = 40)
        String nome,
        @Email
        String email,
        @NotBlank
        @Size(min = 8, max = 40)
        String senha
) {
}
