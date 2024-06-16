package com.be.two.c.apibetwoc.service.venda;

import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.TransacaoRepository;
import com.be.two.c.apibetwoc.service.EstabelecimentoService;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class HistoricoVendaService {

    private final TransacaoRepository transacaoRepository;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    private final EstabelecimentoService estabelecimentoService;

    public Page<Transacao> getHistoricoVenda(int page, int size, Long id) {
        Pageable pageable = PageRequest.of(page, size);
        return transacaoRepository.findAllByPedidoMetodoPagamentoAceitoEstabelecimentoIdAndPedidoStatusDescricaoNotAndPedidoStatusDescricaoNot(
                pageable,
                id,
                StatusPedido.PENDENTE,
                StatusPedido.AGUARDANDO_RETIRADA
        );
    }

    public Page<Transacao> getHistoricoPorFiltro(LocalDate de,
                                                 LocalDate ate,
                                                 String status,
                                                 String nomeMetodoPagamento,
                                                 Integer page,
                                                 Integer size,
                                                 Long id) {
        Pageable pageable = PageRequest.of(page, size);
        StatusPedido statusPedido = status != null ? getStatusPedido(status) : null;

        Specification<Transacao> specification = Specification.where(
                        TransacaoSpecification.comId(id))
                .and(TransacaoSpecification.comMetodoPagamento(nomeMetodoPagamento))
                .and(TransacaoSpecification.comStatus(statusPedido))
                .and(TransacaoSpecification.entreDatas(de, ate))
                .and(TransacaoSpecification.comStatusDiferente(StatusPedido.PENDENTE))
                .and(TransacaoSpecification.comStatusDiferente(StatusPedido.AGUARDANDO_RETIRADA));

        return transacaoRepository.findAll(specification, pageable);
    }

    public List<MetodoPagamentoAceito> listarMetodosPagamentoAceitos(Long id) {
        Estabelecimento estabelecimento = estabelecimentoService.listarPorId(id);
        return metodoPagamentoAceitoRepository.findByEstabelecimento(estabelecimento);
    }

    public byte[] downloadTxt(Long idEstabelecimento) {
        List<Transacao> vendas = transacaoRepository
                .findByPedidoMetodoPagamentoAceitoEstabelecimentoIdAndPedidoStatusDescricaoNotAndPedidoStatusDescricaoNot(idEstabelecimento, StatusPedido.PENDENTE, StatusPedido.AGUARDANDO_RETIRADA);

        Estabelecimento estabelecimento = estabelecimentoService.listarPorId(idEstabelecimento);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            String header = "00NOTA20232";
            header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            header += "01";

            outputStreamWriter.write(header + "\n");

            String corpoComerciante = "02 ";
            corpoComerciante += String.format("%06d ", estabelecimento.getId());
            corpoComerciante += String.format("%-20.20s", estabelecimento.getNome());
            corpoComerciante += String.format("%-14.14s", estabelecimento.getComerciante().getCnpj());

            outputStreamWriter.write(corpoComerciante + "\n");

            for (Transacao t : vendas) {
                String corpo = "03 ";
                corpo += String.format("%06d ", t.getPedido().getId());
                corpo += String.format("%-10.10s ", t.getPedido().getDataHoraPedido().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                corpo += String.format("%-11.11s ", t.getPedido().getItens().get(0).getConsumidor().getCpf());
                corpo += String.format("%-14.14s ", t.getPedido().getIsPagamentoOnline() ? "Pago pelo site" : "Pago na loja");
                corpo += String.format("%-17.17s ", t.getPedido().getMetodoPagamentoAceito().getMetodoPagamento().getDescricao());
                corpo += String.format("%10.2f", t.getValor());

                outputStreamWriter.write(corpo + "\n");
            }
            String trailer = "01";
            trailer += String.format("%010d", vendas.size());

            outputStreamWriter.write(trailer);

            outputStreamWriter.close();
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadCsv(Long idPedido) {
        Transacao venda = transacaoRepository.findByPedidoId(idPedido);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(outputStreamWriter)
                    .withSeparator(';')
                    .build();

            String[] cabecalho = {"Nome", "Codigo SKU", "Preço", "Descrição", "Categoria", "Seção"};
            csvWriter.writeNext(cabecalho);

            for (ItemVenda item : venda.getPedido().getItens()) {
                Produto p = item.getProduto();

                String[] linha = {p.getNome(), p.getCodigoSku(), p.getPreco().toString(), p.getDescricao(), p.getCategoria(), p.getSecao().getDescricao()};

                csvWriter.writeNext(linha);
            }

            csvWriter.close();
            outputStreamWriter.close();
            byte[] csvBytes = byteArrayOutputStream.toByteArray();

            return csvBytes;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StatusPedido getStatusPedido(String status) {
        return switch (status.toLowerCase(Locale.ROOT)) {
            case "aguardando retirada" -> StatusPedido.AGUARDANDO_RETIRADA;
            case "entregue" -> StatusPedido.ENTREGUE;
            case "pendente" -> StatusPedido.PENDENTE;
            case "preparo" -> StatusPedido.PREPARO;
            case "cancelado" -> StatusPedido.CANCELADO;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }
}
