package com.be.two.c.apibetwoc.service.arquivo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoReponseDTO {
    private Resource url;
    private MediaType tipoArquivo;
}
