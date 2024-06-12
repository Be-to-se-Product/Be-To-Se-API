package com.be.two.c.apibetwoc.service.endereco.mapper;

import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoEnderecoCadastroDTO;
import com.be.two.c.apibetwoc.model.Endereco;

public class EnderecoMapper {
    public static Endereco toEndereco(EstabelecimentoEnderecoCadastroDTO estabelecimentoEnderecoCadastroDTO){
        Endereco endereco = new Endereco();
        endereco.setBairro(estabelecimentoEnderecoCadastroDTO.getBairro());
        endereco.setNumero(estabelecimentoEnderecoCadastroDTO.getNumero());
        endereco.setCep(estabelecimentoEnderecoCadastroDTO.getCep());
        endereco.setRua(estabelecimentoEnderecoCadastroDTO.getRua());
        return endereco;
    }
}
