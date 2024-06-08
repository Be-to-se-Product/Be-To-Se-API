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
public class    CadastroProdutoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String codigoSku;
    @Positive
    private Double preco;
    @NotBlank
    private String descricao;
    @NotNull
    private Double precoOferta;
    @NotBlank
    private String codigoBarras;
    @NotBlank
    private String categoria;
    @NotNull
    private Long secao;
    private List<@Valid TagDTO> tags;
}
