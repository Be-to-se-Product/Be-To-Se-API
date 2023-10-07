package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PedidoService {
    private Map<String, Pedido> pedidos = new HashMap<>();

    public Pedido atualizarStatusPedido(String sessionId, String novoStatus) {
        Pedido pedido = pedidos.getOrDefault(sessionId, new Pedido());
        pedido.setStatusDescricao(novoStatus);
        pedidos.put(sessionId, pedido);
        return pedido;
    }
}
