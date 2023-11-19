package com.be.two.c.apibetwoc.controller.consumidor.dto;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ConsumidorCriacaoDto {

    @NotBlank
    public String nome;
    @NotBlank
    private String cpf;
    @NotBlank
    private String celular;
    @NotBlank
    private String genero;
    @Past
    private LocalDate dataNascimento;
    @Valid
    private UsuarioCriacaoDTO usuarioCriacaoDTO;
    //private List<@Valid InteresseCriacaoDto> interesses;
}
