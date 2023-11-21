package com.be.two.c.apibetwoc.service;


import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TesteFilaService {

    private final ProdutoRepository produtoRepository;

    public void atualizar(){
        Produto produto = produtoRepository.findById(1L).get();
        System.out.println(produto.getNome());
        if(produto.getNome().equals("Teste")){
            produto.setNome("Teste2");
            produtoRepository.save(produto);
            return;
        }
        produto.setNome("Teste");
        produtoRepository.save(produto);
    }

}
