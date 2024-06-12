package com.be.two.c.apibetwoc.controller.carrinho.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoRequestDTO {
    @NotNull
    private Integer quantidade;
    @NotNull
    private Long produto;
}
