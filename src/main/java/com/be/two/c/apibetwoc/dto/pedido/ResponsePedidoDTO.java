package com.be.two.c.apibetwoc.dto.pedido;

import com.be.two.c.apibetwoc.model.ItemVenda;
import com.be.two.c.apibetwoc.model.MetodoPagamento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.util.StatusPedido;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ResponsePedidoDTO(Long id,  LocalDateTime dataPedido, StatusPedido statusPedido, Boolean isPagamentoOnline ,
                                String metodoPagamento, List<ResponseItemVendaDto> itens){

}



