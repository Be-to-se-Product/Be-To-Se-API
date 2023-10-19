package com.be.two.c.apibetwoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class CadastroProdutoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Double precoCompra;
    @NotNull
    private Double precoOferta;
    @NotBlank
    private String categoria;
    @NotBlank
    private String codigoSku;
}
