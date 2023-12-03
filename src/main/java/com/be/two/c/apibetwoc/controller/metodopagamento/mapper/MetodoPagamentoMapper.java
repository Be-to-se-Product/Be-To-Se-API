package com.be.two.c.apibetwoc.controller.metodopagamento.mapper;

import com.be.two.c.apibetwoc.controller.metodopagamento.dto.MetodoPagamentoResponseDto;
import com.be.two.c.apibetwoc.model.MetodoPagamento;

public class MetodoPagamentoMapper {
    public static MetodoPagamentoResponseDto toMetodoPagamentoResponseDto(MetodoPagamento metodoPagamento){
        MetodoPagamentoResponseDto metodoPagamentoResponseDto = new MetodoPagamentoResponseDto();
        metodoPagamentoResponseDto.setId(metodoPagamento.getId());
        metodoPagamentoResponseDto.setDescricao(metodoPagamento.getDescricao());
        return metodoPagamentoResponseDto;
    }
}
