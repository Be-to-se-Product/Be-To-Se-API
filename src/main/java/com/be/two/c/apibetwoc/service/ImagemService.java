package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private final Path caminho = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos");

    public void salvarImagem(String base64Image, String nomeReferencia, Produto produto) {

        Imagem imagem = new Imagem();
        imagem.setNomeReferencia(nomeReferencia);
        imagem.setProduto(produto);

        try {
            if (!Files.exists(caminho)) {
                Files.createDirectories(caminho);
            }

            String nomeArquivoFormatado = formatarNomeArquivo(produto.getNome());
            imagem.setNomeImagem(nomeArquivoFormatado);

            byte[] bytes = Base64.getDecoder().decode(base64Image);

            Files.write(caminho.resolve(nomeArquivoFormatado), bytes);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
        imagemRepository.save(imagem);
    }

    public String converterParaBase64(String nomeImagem) {
        try {
            byte[] bytes = Files.readAllBytes(caminho.resolve(nomeImagem));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter imagem", e);
        }
    }

    private String formatarNomeArquivo(String originalFilename) {
        return String.format("%s_%s", UUID.randomUUID(), originalFilename);
    }
    public Imagem obterImagemPorId(Long id) {
        return imagemRepository.findById(id).orElse(null);
    }

    public byte[] obterBytesDaImagem(String nomeImagem) throws IOException {
        return Files.readAllBytes(caminho.resolve(nomeImagem));
    }

}
