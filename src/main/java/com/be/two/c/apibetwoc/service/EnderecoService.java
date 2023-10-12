package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.repository.EnderecoRepository;
import com.be.two.c.apibetwoc.util.ApiCepAberto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ApiCepAberto apiCepAberto;

    public Endereco cadastrar(String cep) {
        Optional<ApiCepAberto.Cep> cepOpt = apiCepAberto.searchByCep(cep);
        if (cepOpt.isEmpty()) throw new EntidadeNaoExisteException("O Cep pesquisado não pôde ser encontrado");
        Endereco endereco = new Endereco();
        ApiCepAberto.Cep info = cepOpt.get();
        endereco.setBairro(info.getBairro());
        endereco.setCep(info.getCep());
        endereco.setRua(info.getLogradouro());
        endereco.setGeolocalizacaoX(info.getLatitude());
        endereco.setGeolocalizacaoY(info.getLongitude());

        return enderecoRepository.save(endereco);
    }
}