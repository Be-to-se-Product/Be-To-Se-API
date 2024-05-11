package com.be.two.c.apibetwoc.service.arquivo;

import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoReponseDTO;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import org.springframework.web.multipart.MultipartFile;

public interface IStorage {


    ArquivoSaveDTO salvarArquivo(MultipartFile file, TipoArquivo tipoArquivo);


    void deletarArquivo(String nomeReferencia, TipoArquivo tipoArquivo);

    ArquivoReponseDTO getArquivo(String nomeReferencia, TipoArquivo tipoArquivo);




}
