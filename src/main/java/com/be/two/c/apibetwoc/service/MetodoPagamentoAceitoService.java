package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetodoPagamentoAceitoService {

    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;

    public void cadastrarMetodosPagamentos(Estabelecimento estabelecimento, List<Long> metodos){

        for (Long l : metodos){
            MetodoPagamento metodo = metodoPagamentoRepository.findById(l)
                    .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum metodo de pagamento com esse id"));

            metodoPagamentoAceitoRepository.save(new MetodoPagamentoAceito(null, estabelecimento, metodo,null));
        }
    }

    public List<MetodoPagamentoAceito> findByEstabelecimentoId(Long id){
        return metodoPagamentoAceitoRepository.findByEstabelecimentoId(id);
    }
}
