package com.be.two.c.apibetwoc.dto.estabelecimento.dto;

import com.be.two.c.apibetwoc.dto.agenda.CadastroAgendaDto;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.Secao;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class CadastroEstabelecimentoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String segmento;
    @PastOrPresent
    private LocalDate dataCriacao;
    //@Pattern(regexp = "^(?:\\(\\d{2}\\)\\s*|\\d{2}-?)?\\d{4,5}-?\\d{4}$")
    private String telefoneContato;
    @NotBlank
    private String enquadramentoJuridico;
    @NotBlank
    private String referenciaInstagram;
    @NotBlank
    private String referenciaFacebook;
    @Email
    private String emailContato;
    @NotNull
    private Long idComerciante;
    @NotNull
    private Endereco endereco;
    @NotNull
    private List<Long> idMetodoPagamento;
    @NotNull
    private List<CadastroAgendaDto> agenda;
    @NotNull
    private List<String> secao;
}


