package com.be.two.c.apibetwoc.service.metodoPagamento;


import com.be.two.c.apibetwoc.model.MetodoPagamento;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;

    public List<MetodoPagamento> listarMetodosPagamentos(){
        return metodoPagamentoRepository.findAll();
    }


}
