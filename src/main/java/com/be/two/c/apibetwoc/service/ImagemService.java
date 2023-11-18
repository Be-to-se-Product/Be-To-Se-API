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
    private final Path caminho = Paths.get("C:\\Users\\Aluno\\Documents\\SprintFacul\\Be-To-Se-API\\arquivos");

    public void salvarImagem(String base64Image, String nomeReferencia, Produto produto) {

        try {
            base64Image = base64Image.replaceAll("^data:image/[a-zA-Z]+;base64,", "");

            byte[] bytes = Base64.getDecoder().decode(base64Image);
            String nomeImagem = produto.getNome() + "-" + nomeReferencia + ".jpg";
            //System.out.println(base64Image);
            Imagem imagem = new Imagem();
            imagem.setNomeReferencia(nomeReferencia);
            imagem.setProduto(produto);
            imagem.setNomeImagem(nomeImagem);
            imagemRepository.save(imagem);

            Path caminhoRelativo = Paths.get("arquivos");

            Path caminhoCompleto = Paths.get(System.getProperty("user.dir"), caminhoRelativo.toString());

            if (!Files.exists(caminhoCompleto)) {
                Files.createDirectories(caminhoCompleto);
            }

            System.out.println(caminhoCompleto.resolve(nomeImagem));
            Files.write(caminhoCompleto.resolve(nomeImagem), bytes);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }

    }

    public String converterParaBase64(String nomeImagem){
        Path caminhoRelativo = Paths.get("arquivos");

        Path caminhoCompleto = Paths.get(System.getProperty("user.dir"), caminhoRelativo.toString());

        try {
            byte[] bytes = Files.readAllBytes(caminhoCompleto.resolve(nomeImagem));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter imagem", e);
        }
    }
}