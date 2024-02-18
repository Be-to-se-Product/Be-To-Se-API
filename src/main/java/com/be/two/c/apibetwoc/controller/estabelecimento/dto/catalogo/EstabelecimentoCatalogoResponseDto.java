package com.be.two.c.apibetwoc.controller.estabelecimento.dto.catalogo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EstabelecimentoCatalogoResponseDto {

    private Long id;
    private String nome;
    private String segmento;
    private LocalDate dataCriacao;
    private String telefoneContato;
    private String enquadramentoJuridico;
    private String referenciaInstagram;
    private String referenciaFacebook;
    private String emailContato;
    private List<ProdutoCatalogoResponseDto> produtos;
    private List<String> imagens;

}
