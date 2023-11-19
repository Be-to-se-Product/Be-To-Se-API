package com.be.two.c.apibetwoc.controller.estabelecimento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadaDto {
    @NotNull(message = "A coordenada X do usuário é obrigatória")
    private Double x;
    @NotNull(message = "A coordenada Y do usuário é obrigatória")
    private Double y;
    @NotNull(message = "A coordenada X de destino é obrigatória")
    private Double toX;
    @NotNull(message = "A coordenada Y de destino é obrigatória")
    private Double toY;
}
