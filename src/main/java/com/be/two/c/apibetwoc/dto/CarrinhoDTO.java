package com.be.two.c.apibetwoc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoDTO {
    @NotNull
    private Integer quantidade;
    @NotNull
    private Long produto;
    @NotNull
    private Long consumidor;
}
