package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.repository.ImagemRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArquivoService {

    private final ImagemRepository imagemRepository;
    private final ProdutoRepository produtoRepository;
    private Path diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos");
//  private Path diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos"); // projeto

    public Imagem upload(MultipartFile file) {

        if (!this.diretorioBase.toFile().exists()) {
            this.diretorioBase.toFile().mkdir();
        }

        String nomeArquivoFormatado = formatarNomeArquivo(file.getOriginalFilename());
        String filePath = this.diretorioBase + "/" + nomeArquivoFormatado;
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Não foi possível salvar o arquivo", null);
        }

        Imagem imagem = new Imagem();
        imagem.setDataCriacao(LocalDate.now());
        imagem.setNomeImagem(file.getOriginalFilename());
        imagem.setNomeReferencia(nomeArquivoFormatado);
        return imagemRepository.save(imagem);
    }
    private String formatarNomeArquivo(String originalFilename) {
        return String.format("%s_%s", UUID.randomUUID(), originalFilename);
    }
}
