package com.be.two.c.apibetwoc.controller.comerciante.dto;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComercianteAtualizarDTO {
    @NotBlank
    private String cnpj;
    @NotBlank
    private String nome;
    @NotBlank
    private String razaoSocial;
    @NotBlank
    private String email;
    @NotBlank
    private String cep;
    @NotBlank
    private String numero;
}
