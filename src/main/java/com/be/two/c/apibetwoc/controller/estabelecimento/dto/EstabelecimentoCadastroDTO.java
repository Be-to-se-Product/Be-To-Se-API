package com.be.two.c.apibetwoc.controller.estabelecimento.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoCadastroDTO {
    @NotBlank
    private String nome;
    @CNPJ
    private String cnpj;
    private String telefone;
    @Email
    private String email;
    private String instagram;
    private String facebook;
    private EstabelecimentoEnderecoCadastroDTO endereco;
    private List<EstabelecimentoCadastroAgendaDTO> agenda;
}


