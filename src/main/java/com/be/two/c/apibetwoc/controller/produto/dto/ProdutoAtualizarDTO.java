package com.be.two.c.apibetwoc.controller.produto.dto;


import com.be.two.c.apibetwoc.controller.tag.TagDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoAtualizarDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String codigoSku;

    @Positive
    private Double preco;
    @NotBlank
    private String descricao;
    @NotNull
    @Positive
    private Double precoOferta;

    private List<String> imagens;

    @NotBlank
    private String codigoBarras;
    @NotBlank
    private String categoria;
    @NotNull
    private Long secao;
    @NotNull
    private Boolean promocaoAtiva;

    @Nullable
    private List<@Valid TagDTO> tag;

}
