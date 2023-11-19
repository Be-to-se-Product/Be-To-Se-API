package com.be.two.c.apibetwoc.controller.secao.dto;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import lombok.Data;

@Data
public class ResponseSecaoDto {
    private Long id;
    private String descricao;
    private Estabelecimento estabelecimento;
}
