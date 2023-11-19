package com.be.two.c.apibetwoc.service.produto;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import com.be.two.c.apibetwoc.service.produto.specification.ProdutoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class ProdutoMapaService{

    private final ImagemService imagemService;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ProdutoRepository produtoRepository;

    public List<Produto> retornarProdutos(Double latitude,Double longitude,Double distancia,String produto,String metodoPagamento){
        List<Integer> estabelecimentos = estabelecimentoRepository.buscarPorLocalizacao(latitude,longitude,distancia);
        System.out.println(distancia);
        System.out.println(latitude);
        System.out.println(longitude);
        System.out.println(produto);
        System.out.println(metodoPagamento);

        Specification<Produto> estabelecimentoSpecification = Specification.where(ProdutoSpecification.filtrarIds(estabelecimentos).and(ProdutoSpecification.name(produto).and(ProdutoSpecification.metodoPagamento(metodoPagamento))));
        List<Produto> produtos = produtoRepository.findAll(estabelecimentoSpecification);
        produtos.stream().forEach(produto1 -> produto1.setImagens(imagemService.formatterImagensURI(produto1.getImagens())));
        return produtos;
    }



}
