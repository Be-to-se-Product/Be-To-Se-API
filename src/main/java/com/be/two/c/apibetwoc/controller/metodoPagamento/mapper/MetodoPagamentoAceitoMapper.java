package com.be.two.c.apibetwoc.controller.metodoPagamento.mapper;

import com.be.two.c.apibetwoc.controller.metodoPagamento.ResponseMetodoPagamentoDto;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;

import java.util.ArrayList;
import java.util.List;

public class MetodoPagamentoAceitoMapper {
    public static List<ResponseMetodoPagamentoDto> of(List<MetodoPagamentoAceito> metodoPagamentoAceito) {
        List<ResponseMetodoPagamentoDto> responseMetodoPagamentoDto = new ArrayList<>();

        for (MetodoPagamentoAceito metodo : metodoPagamentoAceito) {
            ResponseMetodoPagamentoDto response = new ResponseMetodoPagamentoDto();
            response.setId(metodo.getId());
            response.setDescricao(metodo.getMetodoPagamento().getDescricao());
            responseMetodoPagamentoDto.add(response);
        }

        return responseMetodoPagamentoDto;
    }
}
