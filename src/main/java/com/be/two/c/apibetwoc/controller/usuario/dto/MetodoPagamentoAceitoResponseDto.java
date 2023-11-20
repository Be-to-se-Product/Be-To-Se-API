package com.be.two.c.apibetwoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetodoPagamentoAceitoResponseDto {
    private Long id;

    public MetodoPagamentoAceitoResponseDto(Long id) {
        this.id = id;
    }
}
