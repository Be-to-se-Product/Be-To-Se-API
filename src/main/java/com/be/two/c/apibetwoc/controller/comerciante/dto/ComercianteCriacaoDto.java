package com.be.two.c.apibetwoc.controller.comerciante.dto;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComercianteCriacaoDto {

    @NotBlank
    private String cnpj;
    @NotBlank
    private String nome;
    @NotBlank
    private String razaoSocial;
    @Valid
    private UsuarioCriacaoDTO usuarioCriacaoDTO;
    @NotBlank
    private String cep;
}
