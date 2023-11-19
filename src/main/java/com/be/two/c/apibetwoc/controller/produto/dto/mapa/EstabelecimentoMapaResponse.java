package com.be.two.c.apibetwoc.controller.produto.dto.mapa;


import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoMetodoPagamentoResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EstabelecimentoMapaResponse {

    private Long id;
    private String nome;
    private String segmento;
    private LocalDate dataCriacao;
    private EnderecoResponseDTO endereco;
    private List<AgendaResponseDTO> agenda;
    private List<MetodoPagamentoMapaResponse> metodoPagamento;
    private String telefone;
    private String site;
    private LocalDate tempoCarro;
    private LocalDate tempoPessoa;
    private LocalDate tempoBike;
}
