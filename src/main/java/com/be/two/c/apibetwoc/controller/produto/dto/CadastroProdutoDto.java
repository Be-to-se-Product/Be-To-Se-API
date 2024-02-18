package com.be.two.c.apibetwoc.controller.produto.dto;

import com.be.two.c.apibetwoc.controller.tag.TagDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroProdutoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    //Preço base
    @Positive
    private Double preco;

    @NotNull
    private Double precoOferta;

    private String codigoSku;
    @NotBlank
    private String codigoBarras;

    @NotBlank
    private String categoria;

    @NotNull
    private Long secao;

    @NotNull
    private Boolean isAtivo;

    private List<@Valid TagDTO> tags;






}
