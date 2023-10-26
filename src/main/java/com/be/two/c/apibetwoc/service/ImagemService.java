package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private final Path caminho = Paths.get(System.getProperty("java.io.tmpdir"), "arquivos");

    public void salvarImagem(String base64Image, String nomeReferencia, Produto produto) {


        byte[] bytes = Base64.getDecoder().decode(base64Image);
        String nomeImagem = produto.getNome() + "-" + nomeReferencia;

        Imagem imagem = new Imagem();
        imagem.setNomeReferencia(nomeReferencia);
        imagem.setProduto(produto);
        imagem.setNomeImagem(nomeImagem);
        imagemRepository.save(imagem);

        try {
            if (!Files.exists(caminho)) {
                Files.createDirectories(caminho);
            }
            Files.write(caminho.resolve(nomeImagem), bytes);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
    }

}
