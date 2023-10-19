package com.be.two.c.apibetwoc.dto.secao;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Secao;

public class SecaoMapper {
    public static Secao of(CadastroSecaoDto cadastroSecaoDTO, Estabelecimento estabelecimento){
        Secao secao = new Secao();
        secao.setDescricao(cadastroSecaoDTO.getDescricao());
        secao.setEstabelecimento(estabelecimento);
        return secao;
    }

    public static ResponseSecaoDto of(Secao secao){
        ResponseSecaoDto responseSecaoDto = new ResponseSecaoDto();
        responseSecaoDto.setId(secao.getId());
        responseSecaoDto.setDescricao(secao.getDescricao());
        responseSecaoDto.setEstabelecimento(secao.getEstabelecimento());
        return responseSecaoDto;
    }
}
