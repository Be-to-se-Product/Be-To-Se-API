package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.pedido.dto.PedidoCriacaoDto;
import com.be.two.c.apibetwoc.controller.pedido.dto.PedidoDtoStatus;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.StatusPedido;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.PedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;

    @Test
    @DisplayName("Deve lançar exception quando pedido não existe")
    void deveLancarEntidadeNaoExisteExceptionQuandoPedidoNaoExiste() {
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        PedidoDtoStatus pedidoDtoStatus = new PedidoDtoStatus();
        pedidoDtoStatus.setStatus(StatusPedido.PREPARO);

        EntidadeNaoExisteException ex = assertThrows(EntidadeNaoExisteException.class,
                () -> pedidoService.alterarStatusPedido(1L, pedidoDtoStatus));

        assertEquals("O pedido informado não existe", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exception quando método de pagamento não existe")
    void deveLancarEntidadeNaoExisteExceptionQuandoMetodoPagamentoNaoExiste() {

        Mockito.when(metodoPagamentoAceitoRepository
                        .findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        PedidoCriacaoDto pedidoCriacaoDto = new PedidoCriacaoDto(1L,
                "123",
                true);

        EntidadeNaoExisteException ex = assertThrows(EntidadeNaoExisteException.class,
                () -> pedidoService.cadastrar(pedidoCriacaoDto));

        assertEquals("Método de pagamento informado não existe", ex.getMessage());
    }

    @Test
    @DisplayName("Deve alterar status do pedido quando existe no deletar")
    void deveAlterarStatusDoPedidoQuandoExisteNoDeletar() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pedido));

        PedidoDtoStatus pedidoDtoStatus = new PedidoDtoStatus();
        pedidoDtoStatus.setStatus(StatusPedido.PREPARO);

        pedidoService.deletar(1L);

        assertEquals(StatusPedido.CANCELADO, pedido.getStatusDescricao());
    }

    @Test
    @DisplayName("Deve lançar exception quando pedido não existe no deletar")
    void deveLancarEntidadeNaoExisteExceptionQuandoPedidoNaoExisteNoDeletar() {
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        EntidadeNaoExisteException ex = assertThrows(EntidadeNaoExisteException.class,
                () -> pedidoService.deletar(1L));

        assertEquals("Pedido não encontrado", ex.getMessage());
    }

}