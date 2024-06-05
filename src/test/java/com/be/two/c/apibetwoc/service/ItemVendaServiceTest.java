package com.be.two.c.apibetwoc.service;

import static org.mockito.Mockito.*;

import com.be.two.c.apibetwoc.controller.pedido.dto.ItemVendaCreateDto;
import com.be.two.c.apibetwoc.controller.pedido.dto.PedidoCreateDto;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.ItemVenda;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.pedido.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ItemVendaServiceTest {

    @Mock
    private ItemVendaRepository itemVendaRepository;
    @Mock
    private PedidoService pedidoService;
    @Mock
    private ConsumidorRepository consumidorRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private TransacaoService transacaoService;
    @Mock
    private AutenticacaoService autenticacaoService;
    @Mock
    private CarrinhoService carrinhoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ItemVendaService itemVendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCadastrarPedido_EntidadeNaoExisteException_Usuario() {
        Long usuarioId = 1L;
        UsuarioDetalhes usuarioDetalhesMock = mock(UsuarioDetalhes.class);
        when(usuarioDetalhesMock.getId()).thenReturn(usuarioId);
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(usuarioDetalhesMock);
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        PedidoCreateDto pedidoCreateDto = new PedidoCreateDto();

        assertThrows(EntidadeNaoExisteException.class, () -> itemVendaService.cadastrar(pedidoCreateDto));
        verify(usuarioRepository).findById(usuarioId);
    }

    @Test
    void testCadastrarPedido_EntidadeNaoExisteException_Produto() {
        Long usuarioId = 1L;
        Long consumidorId = 1L;
        Long produtoId = 1L;
        UsuarioDetalhes usuarioDetalhesMock = mock(UsuarioDetalhes.class);
        when(usuarioDetalhesMock.getId()).thenReturn(usuarioId);
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(usuarioDetalhesMock);

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getId()).thenReturn(usuarioId);
        Consumidor consumidorMock = mock(Consumidor.class);
        when(consumidorMock.getId()).thenReturn(consumidorId);
        when(usuarioMock.getConsumidor()).thenReturn(consumidorMock);
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));
        when(consumidorRepository.findById(consumidorId)).thenReturn(Optional.of(consumidorMock));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        ItemVendaCreateDto itemVendaCreateDto = new ItemVendaCreateDto();
        itemVendaCreateDto.setIdProduto(produtoId);
        List<ItemVendaCreateDto> itens = List.of(itemVendaCreateDto);
        PedidoCreateDto pedidoCreateDto = new PedidoCreateDto();
        pedidoCreateDto.setItens(itens);

        assertThrows(EntidadeNaoExisteException.class, () -> itemVendaService.cadastrar(pedidoCreateDto));
        verify(usuarioRepository).findById(usuarioId);
        verify(consumidorRepository).findById(consumidorId);
        verify(produtoRepository).findById(produtoId);
    }
}
