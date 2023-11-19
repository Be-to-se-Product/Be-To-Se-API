package com.be.two.c.apibetwoc.dto.comerciante;

import com.be.two.c.apibetwoc.dto.UsuarioCriacaoDTO;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ComercianteDetalhamentoDto {
    private String cnpj;
    private String nome;
    private String razaoSocial;
    private String email;
    private Endereco endereco;

    public ComercianteDetalhamentoDto(Comerciante comerciante, Endereco endereco) {
        this.nome = comerciante.getNome();
        this.cnpj = comerciante.getCnpj();
        this.razaoSocial = comerciante.getRazaoSocial();
        this.email = comerciante.getUsuario().getEmail();
        this.endereco = endereco;

    }
}
