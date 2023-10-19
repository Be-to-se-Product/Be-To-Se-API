package com.be.two.c.apibetwoc.dto.secao;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroSecaoDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String descricao;
    @NotNull
    @Positive
    private Long idEstabelecimento;
}