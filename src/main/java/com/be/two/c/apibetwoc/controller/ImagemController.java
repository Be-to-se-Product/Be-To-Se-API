package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.service.ImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
public class ImagemController {

    private final ImagemService imagemService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        Imagem imagem = imagemService.obterImagemPorId(id);

        if (imagem == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            byte[] imagemBytes = imagemService.obterBytesDaImagem(imagem.getNomeImagem());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("inline", imagem.getNomeImagem());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imagemBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
