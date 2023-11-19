package com.be.two.c.apibetwoc.controller.consumidor.dto;

import com.be.two.c.apibetwoc.dto.InteresseCriacaoDto;
import com.be.two.c.apibetwoc.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

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
