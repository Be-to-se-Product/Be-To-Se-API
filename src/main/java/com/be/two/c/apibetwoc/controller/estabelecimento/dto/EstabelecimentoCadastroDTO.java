package com.be.two.c.apibetwoc.controller.estabelecimento.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoCadastroDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String segmento;
    //@Pattern(regexp = "^(?:\\(\\d{2}\\)\\s*|\\d{2}-?)?\\d{4,5}-?\\d{4}$")
    private String telefoneContato;
    @NotBlank
    private String referenciaInstagram;
    @NotBlank
    private String referenciaFacebook;
    @Email
    private String emailContato;

    @NotNull
    private EstabelecimentoEnderecoCadastroDTO endereco;
    @NotNull
    private List<Long> metodoPagamento;
    @NotNull
    private List<EstabelecimentoCadastroAgendaDTO> agenda;
    @NotNull
    private List<String> secao;
}


