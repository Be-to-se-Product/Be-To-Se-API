package com.be.two.c.apibetwoc.dto.secao;

import com.be.two.c.apibetwoc.dto.estabelecimento.EstabelecimentoSimpleResponse;
import com.be.two.c.apibetwoc.model.Secao;
import lombok.Data;

import java.util.List;

@Data
public class SecaoDetalhamentoDto {
    private Long id;
    private String descricao;
    private EstabelecimentoSimpleResponse estabelecimento;
    public SecaoDetalhamentoDto(Secao secao){
        this.id = secao.getId();
        this.descricao = secao.getDescricao();
        this.estabelecimento = new EstabelecimentoSimpleResponse(secao.getEstabelecimento());
    }
}
