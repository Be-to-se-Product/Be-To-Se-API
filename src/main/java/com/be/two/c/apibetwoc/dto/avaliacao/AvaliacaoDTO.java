package com.be.two.c.apibetwoc.dto.avaliacao;

import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class AvaliacaoDTO {
    @DecimalMin(value ="1")
    @DecimalMax(value = "5")
    private Integer qtdEstrela;
    @NotBlank
    private String comentario;
    @NotNull
    private Long consumidor;
    @NotNull
    private Long produto;
}
