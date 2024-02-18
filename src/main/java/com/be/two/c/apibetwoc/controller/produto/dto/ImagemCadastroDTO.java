package com.be.two.c.apibetwoc.controller.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagemCadastroDTO {

    private String nome;
    private Integer id;
    private MultipartFile imagem;
}
