package com.be.two.c.apibetwoc.controller.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemVendaCreateDto {
    @NotNull
    private Long idProduto;
    @Min(value = 1)
    private Integer quantidade;
}
