package com.be.two.c.apibetwoc.controller.consumidor.dto;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
public class ConsumidorCriacaoDto {

    @NotBlank
    @Size(min = 3)
    public String nome;
    @NotBlank
    @CPF
    private String cpf;
    @NotBlank
    private String celular;
    @NotBlank
    private String genero;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Valid
    private UsuarioCriacaoDTO usuario;

}
