package com.be.two.c.apibetwoc.service.arquivo;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.service.AutenticacaoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoReponseDTO;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.arquivo.exception.ArquivoNaoPermitidoException;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArquivoService implements IStorage {
  private final AutenticacaoService autenticacaoService;
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

  private final Map<TipoArquivo, String> diretorios = new HashMap<>() {{
    put(TipoArquivo.IMAGEM, "imagens/");
    put(TipoArquivo.DOCUMENTO, "documentos/");
  }};
  private final String DIRETORIO_PRINCIPAL = "./";
  
  @Override
  public void deletarArquivo(String nomeReferencia, TipoArquivo tipoArquivo){
    Path caminhoArquivo = Paths.get(diretorios.get(tipoArquivo)+nomeReferencia).toAbsolutePath().normalize();
    try {
      Files.delete(caminhoArquivo);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public ArquivoReponseDTO getArquivo(String nomeReferencia, TipoArquivo tipoArquivo) {
    Path url = Paths.get(diretorios.get(tipoArquivo)+nomeReferencia).toAbsolutePath().normalize();

    String tipoImagem = nomeReferencia.substring(nomeReferencia.lastIndexOf(".") + 1);
    Resource resource;
    try {
      resource = new UrlResource(url.toUri());
      System.out.println(resource.getFilename());
    } catch (MalformedURLException e) {
      throw new EntidadeNaoExisteException("Imagem não existe");
    }
    return new ArquivoReponseDTO(resource, tiposArquivosPermitidos.get(tipoImagem));
  }

  @Override
  public ArquivoSaveDTO salvarArquivo(MultipartFile file, TipoArquivo tipoArquivo) {
    UsuarioDetalhes usuarioDetalhes = autenticacaoService.loadUsuarioDetails();

    if(!tiposArquivosPermitidos.containsKey(Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1))){
      throw new ArquivoNaoPermitidoException();
    }
    
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



  public ArquivoSaveDTO salvarArquivoPilha(MultipartFile file, TipoArquivo tipoArquivo, PilhaObj<ArquivoSaveDTO> pilhaArquivos) {
    verificarTipoArquivo(file,pilhaArquivos);
    ArquivoSaveDTO arquivo =salvarArquivo(file,tipoArquivo);
    pilhaArquivos.push(arquivo);
    return arquivo;
  }



  public void verificarTipoArquivo(MultipartFile file, PilhaObj<ArquivoSaveDTO> pilhaArquivos) {
    String extensaoArquivo = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    if(!tiposArquivosPermitidos.containsKey(extensaoArquivo)){
    while(!pilhaArquivos.isEmpty()){
      ArquivoSaveDTO arquivo = pilhaArquivos.pop();
      deletarArquivo(arquivo.getNomeArquivoReferencia(),arquivo.getTipoArquivo());
    }
      throw new ArquivoNaoPermitidoException();
    }
  }




}