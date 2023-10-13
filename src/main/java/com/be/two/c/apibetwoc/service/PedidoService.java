package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final SimpMessagingTemplate messagingTemplate;
    private final PedidoRepository pedidoRepository;

    public void alterarStatusPedido(Long idPedido, String novoStatus) {
        Pedido pedido = pedidoRepository
                .findById(idPedido)
                .orElseThrow(
                        () -> new EntidadeNaoExisteException("O pedido informado não existe"));

        pedido.setStatusDescricao(novoStatus);
        StatusPedidoMessage mensagem = new StatusPedidoMessage();
        mensagem.setIdPedido(idPedido);
        mensagem.setNovoStatus(novoStatus);
        messagingTemplate.convertAndSend("/topic/statusPedido", mensagem);

    }
}
