package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import com.be.two.c.apibetwoc.model.Agenda;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.model.Secao;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseEstabelecimentoDto {
    private Long id;
    private String nome;
    private String segmento;
    private LocalDate dataCriacao;
    private String telefoneContato;
    private String enquadramentoJuridico;
    private String referenciaInstagram;
    private String referenciaFacebook;
    private String emailContato;
    private Boolean isAtivo;
    private Long idComerciante;
    private String cnpj;
    private Endereco endereco;
    private List<Agenda> agenda;
    private List<MetodoPagamentoAceito> metodoPagamento;
    private List<Secao> secao;
}
