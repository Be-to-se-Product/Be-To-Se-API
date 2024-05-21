package com.be.two.c.apibetwoc.service.arquivo;

import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoReponseDTO;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Profile("prod")
@Service
@RequiredArgsConstructor

public class CloudinaryService implements IStorage {

    private final Cloudinary cloudinary;
    private final Map<TipoArquivo, String> diretorios = new HashMap<>() {{
        put(TipoArquivo.IMAGEM, "imagens/");
        put(TipoArquivo.DOCUMENTO, "documentos/");
    }};

    Map<String, MediaType> tiposArquivosPermitidos = new HashMap<>() {{

        put(TipoArquivo.IMAGEM.name(), MediaType.IMAGE_PNG);
        put(TipoArquivo.DOCUMENTO.name(), MediaType.APPLICATION_OCTET_STREAM);
    }};


    @Override
    public ArquivoSaveDTO salvarArquivo(MultipartFile file, TipoArquivo tipoArquivo) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            String diretorio = diretorios.get(tipoArquivo);
            options.put("folder", diretorio);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            String url = cloudinary.url().secure(true).generate(publicId);
            return new ArquivoSaveDTO(file.getOriginalFilename(), tipoArquivo, url, LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArquivoSaveDTO salvarArquivo(MultipartFile file, TipoArquivo tipoArquivo, PilhaObj<ArquivoSaveDTO> arquivos) {
       ArquivoSaveDTO arquivo = salvarArquivo(file,tipoArquivo);
       arquivos.push(arquivo);
       return arquivo;
    }

    @Override
    public void deletarArquivo(String nomeReferencia, TipoArquivo tipoArquivo) {
        System.out.printf("Excluiu");
    }

    @Override
    public ArquivoReponseDTO getArquivo(String nomeReferencia, TipoArquivo tipoArquivo)  {
        try {
            return new ArquivoReponseDTO(new UrlResource(nomeReferencia), tiposArquivosPermitidos.get(tipoArquivo.name()));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
