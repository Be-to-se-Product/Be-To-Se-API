package com.be.two.c.apibetwoc.dto;

import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroEstabelecimentoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String segmento;
    @NotBlank
    private LocalDate dataCriacao;
    @Pattern(regexp = "^(?:\\(\\d{2}\\)\\s*|\\d{2}-?)?\\d{4,5}-?\\d{4}$")
    private String telefoneContato;
    private String enquadramentoJuridico;
    private String referenciaInstagram;
    private String referenciaFacebook;
    @Email
    private String emailContato;
    private Boolean isAtivo;
    private Comerciante comerciante;
    private Endereco endereco;

}


