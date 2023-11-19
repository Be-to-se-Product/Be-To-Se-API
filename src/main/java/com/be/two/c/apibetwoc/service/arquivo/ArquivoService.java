package com.be.two.c.apibetwoc.service.arquivo;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.repository.ImagemRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.be.two.c.apibetwoc.service.AutenticacaoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoReponseDTO;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArquivoService {

  private final AutenticacaoService autenticacaoService;
  private final ImagemRepository imagemRepository;
  private final ProdutoRepository produtoRepository;
  Map<String, MediaType> tiposArquivosPermitidos = new HashMap<>() {{
    put("png", MediaType.IMAGE_PNG);
    put("jpg", MediaType.IMAGE_JPEG);
    put("jpeg", MediaType.IMAGE_JPEG);
    put("pdf", MediaType.APPLICATION_PDF);
    put("doc", MediaType.APPLICATION_OCTET_STREAM);
    put("docx", MediaType.APPLICATION_OCTET_STREAM);
    put("xls", MediaType.APPLICATION_OCTET_STREAM);
    put("xlsx", MediaType.APPLICATION_OCTET_STREAM);
  }};

  final Map<TipoArquivo, String> diretorios = new HashMap<>() {{
    put(TipoArquivo.IMAGEM, "imagens/");
    put(TipoArquivo.DOCUMENTO, "documentos/");
  }};
    final String DIRETORIO_PRINCIPAL = "./";



  private final HttpServletRequest request;
  public ArquivoSaveDTO salvarArquivo(MultipartFile file, TipoArquivo tipoArquivo) {
    UsuarioDetalhes usuarioDetalhes = autenticacaoService.loadUsuarioDetails();
    String diretorio = DIRETORIO_PRINCIPAL + diretorios.get(tipoArquivo);
    try {
      Path caminhoArquivo = Paths.get(diretorio).normalize();
      if (!Files.exists(caminhoArquivo)) {
        Files.createDirectories(caminhoArquivo);
      }

      String nomeArquivo = usuarioDetalhes.getId() + "" + UUID.randomUUID()+file.getOriginalFilename().replaceAll(" ", "");
      Path caminhoCompleto = caminhoArquivo.resolve(nomeArquivo).normalize();
      Files.copy(file.getInputStream(), caminhoCompleto);
      return new ArquivoSaveDTO(file.getOriginalFilename(), tipoArquivo,nomeArquivo, LocalDateTime.now());
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar o arquivo");
    }
  }




    public ArquivoReponseDTO getArquivo(String nomeReferencia, TipoArquivo tipoArquivo) {

        Path url = Paths.get(diretorios.get(tipoArquivo)+nomeReferencia).toAbsolutePath().normalize();
        String tipoImagem = nomeReferencia.substring(nomeReferencia.lastIndexOf(".") + 1);
        Resource resource = null;
        try {
            resource = new UrlResource(url.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArquivoReponseDTO(resource, tiposArquivosPermitidos.get(tipoImagem));
    }
}