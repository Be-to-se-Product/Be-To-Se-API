package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import com.be.two.c.apibetwoc.model.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstabelecimentoAtualizarDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String segmento;
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
    private Endereco endereco;
}
