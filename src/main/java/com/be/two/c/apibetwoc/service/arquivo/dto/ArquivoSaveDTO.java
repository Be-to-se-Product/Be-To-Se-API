package com.be.two.c.apibetwoc.service.arquivo.dto;

import com.be.two.c.apibetwoc.util.TipoArquivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoSaveDTO {
    private String nomeArquivo;
    private TipoArquivo tipoArquivo;
    private String nomeArquivoReferencia;
    private LocalDateTime dataHoraSalvo;
}
