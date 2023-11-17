package com.be.two.c.apibetwoc.service.produto;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.be.two.c.apibetwoc.service.Carro;
import com.be.two.c.apibetwoc.service.ITempoPercurso;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoMapaService{


    private final EstabelecimentoRepository estabelecimentoRepository;


    public List<Produto> retornarProdutos(Double latitude,Double longitude,Double distancia,String produto){
        List<Produto> estabelecimentos = estabelecimentoRepository.buscarPorLocalizacao(latitude,longitude,distancia);
        return estabelecimentos;

    }



}
