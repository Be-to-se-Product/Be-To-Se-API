package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.model.Transacao;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoVendaService {

    private final TransacaoRepository transacaoRepository;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    private final EstabelecimentoService estabelecimentoService;

    public Page<Transacao> getHistoricoVenda(int page, int size, Long id) {
        Pageable pageable = PageRequest.of(page, size);
        return transacaoRepository.findAllByPedidoMetodoPagamentoAceitoEstabelecimentoId(pageable, id);
    }

    public Page<Transacao> getHistoricoPorFiltro(String de,
                                                 String ate,
                                                 String status,
                                                 String nomeMetodoPagamento,
                                                 int page,
                                                 int size,
                                                 Long id) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Transacao> specification = Specification.where(null);

        if (de != null && ate != null) {
            LocalDate deData = LocalDate.parse(de);
            LocalDate ateData = LocalDate.parse(ate);
            specification = specification.or(TransacaoSpecification.entreDatas(deData, ateData));
        }
        if (status != null) {
            specification = specification.or(TransacaoSpecification.comStatus(status));
        }
        if (nomeMetodoPagamento != null) {
            specification = specification.or(TransacaoSpecification.comMetodoPagamento(nomeMetodoPagamento));
        }
        System.out.println(specification);
        System.out.println(id);
        System.out.println(pageable);
        return transacaoRepository.findAllByPedidoMetodoPagamentoAceitoEstabelecimentoId(specification, pageable, id);
    }

    public List<MetodoPagamentoAceito> listarMetodosPagamentoAceitos(Long id) {
        Estabelecimento estabelecimento = estabelecimentoService.listarPorId(id);
        return metodoPagamentoAceitoRepository.findByEstabelecimento(estabelecimento);
    }
}
