package com.be.two.c.apibetwoc.service.imagem.mapper;

import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;

public class ImagemMapper {

    public static Imagem of(ArquivoSaveDTO arquivoSaveDTO, Produto produto){
        Imagem imagem = new Imagem(null,arquivoSaveDTO.getNomeArquivo(),arquivoSaveDTO.getNomeArquivoReferencia(),arquivoSaveDTO.getDataHoraSalvo(),null,produto);
        return imagem;
    }
}
