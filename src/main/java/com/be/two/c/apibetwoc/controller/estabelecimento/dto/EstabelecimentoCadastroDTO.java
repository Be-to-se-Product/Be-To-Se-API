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
    private String telefoneContato;
    @Email
    private String emailContato;
    private String referenciaInstagram;
    private String referenciaFacebook;
    private EstabelecimentoEnderecoCadastroDTO endereco;
    private List<EstabelecimentoCadastroAgendaDTO> agenda;
    private String segmento;
    private List<Long> metodoPagamento;
    @NotNull
    private List<String> secao;
}


