package com.be.two.c.apibetwoc.dto.secao;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import lombok.Data;

@Data
public class ResponseSecaoDto {
    private Long id;
    private String descricao;
    private Estabelecimento estabelecimento;
}
