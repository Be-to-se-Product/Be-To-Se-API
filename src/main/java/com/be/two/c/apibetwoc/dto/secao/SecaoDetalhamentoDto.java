package com.be.two.c.apibetwoc.dto.secao;

import com.be.two.c.apibetwoc.model.Secao;
import lombok.Data;

@Data
public class SecaoDetalhamentoDto {
    private Long id;
    private String descricao;

    public SecaoDetalhamentoDto(Secao secao){
        this.id = secao.getId();
        this.descricao = secao.getDescricao();
    }
}
