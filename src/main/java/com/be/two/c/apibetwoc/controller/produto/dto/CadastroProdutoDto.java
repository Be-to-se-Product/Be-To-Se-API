package com.be.two.c.apibetwoc.controller.produto.dto;

import com.be.two.c.apibetwoc.dto.TagDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Base64;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroProdutoDto {
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
    @Nullable
    private List<@Valid TagDTO> tag;
    @Nullable
    private String imagem1;
    @Nullable
    private String imagem2;
    @Nullable
    private String imagem3;
    @Nullable
    private String imagem4;
    @Nullable
    private String imagem5;

}
