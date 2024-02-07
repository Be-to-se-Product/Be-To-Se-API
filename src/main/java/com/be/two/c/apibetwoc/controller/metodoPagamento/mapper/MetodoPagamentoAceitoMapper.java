package com.be.two.c.apibetwoc.controller.metodoPagamento.mapper;
import com.be.two.c.apibetwoc.controller.metodoPagamento.dto.MetodoPagamentoResponseDTO;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;

import java.util.ArrayList;
import java.util.List;

public class MetodoPagamentoAceitoMapper {
    public static List<MetodoPagamentoResponseDTO> of(List<MetodoPagamentoAceito> metodoPagamentoAceito) {
        List<MetodoPagamentoResponseDTO> responseMetodoPagamentoDto = new ArrayList<>();

        for (MetodoPagamentoAceito metodo : metodoPagamentoAceito) {
            MetodoPagamentoResponseDTO response = new MetodoPagamentoResponseDTO();
            response.setId(metodo.getId());
            response.setDescricao(metodo.getMetodoPagamento().getDescricao());
            responseMetodoPagamentoDto.add(response);
        }

        return responseMetodoPagamentoDto;
    }

}
