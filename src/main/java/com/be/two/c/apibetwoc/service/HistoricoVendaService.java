package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoVendaService {

    private final TransacaoRepository transacaoRepository;

    public Page<Transacao> getHistoricoVenda(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transacaoRepository.findAll(pageable);
    }

    public Page<Transacao> getHistoricoPorFiltro(LocalDate dataIncial,
                                                 LocalDate dataFinal,
                                                 String status,
                                                 String nomeMetodoPagamento,
                                                 int page,
                                                 int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Transacao> specification = Specification.where(null);
        if (dataIncial != null && dataFinal != null) {
            specification = specification.and(TransacaoSpecification.entreDatas(dataIncial, dataFinal));
        }
        if (status != null) {
            specification = specification.and(TransacaoSpecification.comStatus(status));
        }
        if (nomeMetodoPagamento != null) {
            specification = specification.and(TransacaoSpecification.comMetodoPagamento(nomeMetodoPagamento));
        }

        return transacaoRepository.findAll(specification, pageable);
    }
}
