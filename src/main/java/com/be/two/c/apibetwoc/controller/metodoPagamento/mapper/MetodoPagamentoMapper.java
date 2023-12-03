package com.be.two.c.apibetwoc.controller.metodoPagamento.mapper;

import com.be.two.c.apibetwoc.controller.metodoPagamento.dto.MetodoPagamentoResponseDTO;
import com.be.two.c.apibetwoc.model.MetodoPagamento;

public class MetodoPagamentoMapper {

    public static MetodoPagamentoResponseDTO toMetodoPagamentoResponse(MetodoPagamento metodoPagamento){
        MetodoPagamentoResponseDTO metodoPagamentoDto = new MetodoPagamentoResponseDTO();
        metodoPagamentoDto.setId(metodoPagamento.getId());
        metodoPagamentoDto.setDescricao(metodoPagamento.getDescricao());
        return metodoPagamentoDto;
    }
}
