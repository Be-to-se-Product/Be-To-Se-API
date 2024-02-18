package com.be.two.c.apibetwoc.controller.produto.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoImagemResponseDTO {
    private Integer id;
    private String url;
    private String nome;
}
