package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.pedido.PedidoCriacaoDto;
import com.be.two.c.apibetwoc.dto.pedido.PedidoMapper;
import com.be.two.c.apibetwoc.dto.pedido.ResponsePedidoDTO;

import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.PedidoRepository;
import com.be.two.c.apibetwoc.util.ListaObj;
import com.be.two.c.apibetwoc.util.StatusPedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final SimpMessagingTemplate messagingTemplate;
    private final PedidoRepository pedidoRepository;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    public void alterarStatusPedido(Long idPedido, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository
                .findById(idPedido)
                .orElseThrow(
                        () -> new EntidadeNaoExisteException("O pedido informado não existe"));

        pedido.setStatusDescricao(novoStatus);
        StatusPedidoMessage mensagem = new StatusPedidoMessage();
        mensagem.setIdPedido(idPedido);

        messagingTemplate.convertAndSend("/topic/statusPedido", mensagem);
    }

    public Pedido cadastrar(@Valid PedidoCriacaoDto pedidoCriacaoDto){
        Pedido pedido = PedidoMapper.of(pedidoCriacaoDto);
        MetodoPagamentoAceito metodoPagamentoAceito = metodoPagamentoAceitoRepository.findById(pedidoCriacaoDto.idMetodoPagamento())
                .orElseThrow(() -> new EntidadeNaoExisteException("Método de pagamento informado não existe"));
        pedido.setMetodoPagamentoAceito(metodoPagamentoAceito);
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long idPedido) {
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
        if (pedido.isPresent()) {
            return pedido.get();
        }
        throw new EntidadeNaoExisteException("Pedido não encontrado");
    }

    public List<ResponsePedidoDTO> listarPorConsumidor(Long idConsumidor) {
        List <Pedido> pedidos = pedidoRepository.searchByConsumidor(idConsumidor);
        return pedidos.stream().map(PedidoMapper::of).toList();
    }

    public ListaObj<ResponsePedidoDTO> listarPorEstabelecimento(Long idEstabelecimento) {
        List<Pedido> pedidos = pedidoRepository.searchByEstabelecimento(idEstabelecimento);

        ListaObj<ResponsePedidoDTO> listaPedidos = new ListaObj<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            listaPedidos.adiciona(PedidoMapper.of(pedido));
        }

        return listaPedidos;
    }

    public List<Pedido> listarPorEstabelecimentoEStatus(Long idEstabelecimento, String status) {
        return pedidoRepository.searchByEstabelecimentoEStatus(idEstabelecimento, status);
    }

//    public ListaObj<ResponsePedidoDto> ordenacao(ListaObj<ResponsePedidoDto> listaPedidos) {
//        int tamanho = listaPedidos.getTamanho();
//
//        for (int i = 0; i < tamanho - 1; i++) {
//            for (int j = 0; j < tamanho - 1 - i; j++) {
//                ResponsePedidoDto pedidoAtual = listaPedidos.getElemento(j);
//                ResponsePedidoDto proximoPedido = listaPedidos.getElemento(j + 1);
//
//                if (pedidoAtual.getDataHoraPedido().isBefore(proximoPedido.getDataHoraPedido())) {
//                    listaPedidos.troca(j, j + 1);
//                }
//            }
//        }
//
//        return listaPedidos;
//    }


    public void deletar(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoExisteException("Pedido não encontrado"));
    pedido.setStatusDescricao(StatusPedido.PENDENTE);
    }
}