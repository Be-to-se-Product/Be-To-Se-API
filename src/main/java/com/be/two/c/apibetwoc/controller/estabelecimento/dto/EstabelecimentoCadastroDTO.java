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
    //    @NotBlank
//    @CNPJ
//    private String cnpj;
    @NotBlank
    private String telefoneContato;
    @Email
    private String emailContato;
    @NotNull
    private List<String> secao;
    @NotNull
    private EstabelecimentoEnderecoCadastroDTO endereco;
    private List<EstabelecimentoCadastroAgendaDTO> agenda;
    private String referenciaInstagram;
    private String referenciaFacebook;
    private String segmento;
}


