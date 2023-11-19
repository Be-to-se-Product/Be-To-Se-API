package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.secao.dto.CadastroSecaoDto;
import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Secao;
import com.be.two.c.apibetwoc.repository.SecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecaoService {
    private final SecaoRepository secaoRepository;
    private final EstabelecimentoService estabelecimentoService;

    public List<Secao> listarSecoes(){
        return secaoRepository.findAll();
    }

    public List<Secao> listarPorEstabelecimento(Long id){
        estabelecimentoService.listarPorId(id);
        return secaoRepository.findByEstabelecimentoId(id);
    }

    public Secao cadastrarSecao(CadastroSecaoDto secao){
        Estabelecimento estabelecimento = estabelecimentoService.listarPorId(secao.getIdEstabelecimento());

        return secaoRepository.save(SecaoMapper.of(secao, estabelecimento));
    }

    public Secao listarSecaoPorDescricao(String descricao){
        return secaoRepository.findByDescricao(descricao);
    }
}
