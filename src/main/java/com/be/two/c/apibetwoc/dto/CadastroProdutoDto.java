package com.be.two.c.apibetwoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroProdutoDto {
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;
    @NotBlank(message = "A descrição do produto é obrigatória")
    private String descricao;
    @NotNull(message = "O preço de compra do produto é obrigatório")
    private Double precoCompra;
    @NotNull(message = "O preço de venda do produto é obrigatório")
    private Double precoVenda;
    @NotBlank(message = "A url da imagem do produto é obrigatória")
    private String urlImagem;
    @NotBlank(message = "A tag do produto é obrigatória")
    private String tag;
    @NotBlank(message = "A categoria do produto é obrigatória")
    private String categoria;
    @NotBlank(message = "O código GTIN do produto é obrigatório")
    private String codigoGtin;
}
