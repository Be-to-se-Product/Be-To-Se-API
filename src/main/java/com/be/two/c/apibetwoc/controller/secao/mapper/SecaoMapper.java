package com.be.two.c.apibetwoc.controller.secao.mapper;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoEstabelecimentoResponseDTO;
import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoSecaoResponseDTO;
import com.be.two.c.apibetwoc.controller.secao.dto.CadastroSecaoDto;
import com.be.two.c.apibetwoc.controller.secao.dto.ResponseSecaoDto;
import com.be.two.c.apibetwoc.controller.secao.dto.SecaoDetalhamentoDto;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Secao;

import java.util.ArrayList;
import java.util.List;

public class SecaoMapper {
    public static Secao of(CadastroSecaoDto cadastroSecaoDTO, Estabelecimento estabelecimento){
        Secao secao = new Secao();
        secao.setDescricao(cadastroSecaoDTO.getDescricao());
        secao.setEstabelecimento(estabelecimento);
        return secao;
    }

    public static List<Secao> of(List<String> descricao, Estabelecimento estabelecimento){
        List<Secao> secoes = new ArrayList<>();
        for (String s : descricao) {
            Secao secao = new Secao();
            secao.setDescricao(s);
            secao.setEstabelecimento(estabelecimento);
            secoes.add(secao);
        }
        return secoes;
    }

    public static ResponseSecaoDto of(Secao secao){
        ResponseSecaoDto responseSecaoDto = new ResponseSecaoDto();
        responseSecaoDto.setId(secao.getId());
        responseSecaoDto.setDescricao(secao.getDescricao());
        return responseSecaoDto;
    }

    public static List<Secao> toSecaoEstabelecimento(List<String> secoes, Estabelecimento estabelecimento){
        List<Secao> secaoList = new ArrayList<>();
        for(String s : secoes){
            Secao secao = new Secao();
            secao.setDescricao(s);
            secao.setEstabelecimento(estabelecimento);
            secaoList.add(secao);
        }
        return secaoList;
    }
}
