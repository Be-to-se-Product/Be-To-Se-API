package com.be.two.c.apibetwoc.controller.estabelecimento.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseEstabelecimentoDto {
    private Long id;
    private String nome;
    private String segmento;
    private String telefoneContato;
    private String referenciaInstagram;
    private String referenciaFacebook;
    private String emailContato;
    private Long idComerciante;
    private String cnpj;
    private EstabelecimentoEnderecoResponseDTO endereco;
    private List<EstabelecimentoAgendaResponseDTO> agenda;
    private List<EstabelecimentoMetodoPagamentoResponseDTO> metodoPagamento;
    private List<EstabelecentoSecaoResponseDTO> secao;
    private List<String> imagens;

}
