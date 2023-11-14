package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoricoVendaService {

    private final TransacaoRepository transacaoRepository;

    public Page<Transacao> getHistoricoVenda(int page, int size,Long id) {
        Pageable pageable = PageRequest.of(page, size);
        return transacaoRepository.findAllByPedidoMetodoPagamentoAceitoEstabelecimentoId(pageable, id);
    }

    public Page<Transacao> getHistoricoPorFiltro(LocalDateTime de,
                                                 LocalDateTime ate,
                                                 String status,
                                                 String nomeMetodoPagamento,
                                                 int page,
                                                 int size,
                                                 Long id) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Transacao> specification = Specification.where(null);
        if (de != null && ate != null) {
            specification = specification.and(TransacaoSpecification.entreDatas(de, ate));
        }
        if (status != null) {
            specification = specification.and(TransacaoSpecification.comStatus(status));
        }
        if (nomeMetodoPagamento != null) {
            specification = specification.and(TransacaoSpecification.comMetodoPagamento(nomeMetodoPagamento));
        }

        return transacaoRepository.findAllByPedidoMetodoPagamentoAceitoEstabelecimentoId(specification, pageable, id);
    }
}
