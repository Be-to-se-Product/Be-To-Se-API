package com.be.two.c.apibetwoc.infra.socket;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

        @Autowired
        private  PedidoService pedidoService;


        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String status = message.getPayload();
            if (isValidStatus(status)) {
                Pedido pedido = pedidoService.atualizarStatusPedido(session.getId(), status);
                session.sendMessage(new TextMessage("Status atualizado para: " + pedido.getStatusDescricao()));
            } else {
                session.sendMessage(new TextMessage("Status inválido"));
            }
        }

        private boolean isValidStatus(String status) {
            String[] statusValidos = {"pendente", "recusado", "em preparação", "pronto para a entrega"};
            for (String statusValido : statusValidos) {
                if (statusValido.equalsIgnoreCase(status)) {
                    return true;
                }
            }
            return false;
        }
}
