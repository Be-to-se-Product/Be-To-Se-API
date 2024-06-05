package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.avaliacao.dto.AvaliacaoRequestDTO;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Avaliacao;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.AvaliacaoRepository;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private AutenticacaoService autenticacaoService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPublicar_UsuarioNaoEncontrado() {
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(mock(UsuarioDetalhes.class));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        AvaliacaoRequestDTO avaliacaoRequestDTO = new AvaliacaoRequestDTO();

        assertThrows(EntidadeNaoExisteException.class, () -> avaliacaoService.publicar(avaliacaoRequestDTO));
        verify(usuarioRepository).findById(anyLong());
    }

    @Test
    void testPublicar_UsuarioSemConsumidor() {
        Usuario usuarioMock = mock(Usuario.class);
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(mock(UsuarioDetalhes.class));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioMock));

        AvaliacaoRequestDTO avaliacaoRequestDTO = new AvaliacaoRequestDTO();

        assertThrows(EntidadeNaoExisteException.class, () -> avaliacaoService.publicar(avaliacaoRequestDTO));
        verify(usuarioRepository).findById(anyLong());
    }

    @Test
    void testPublicar_ProdutoNaoEncontrado() {
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getConsumidor()).thenReturn(mock(Consumidor.class));
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(mock(UsuarioDetalhes.class));
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioMock));
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        AvaliacaoRequestDTO avaliacaoRequestDTO = new AvaliacaoRequestDTO();
        avaliacaoRequestDTO.setProduto(1L);

        assertThrows(EntidadeNaoExisteException.class, () -> avaliacaoService.publicar(avaliacaoRequestDTO));
        verify(usuarioRepository).findById(anyLong());
        verify(produtoRepository).findById(anyLong());
    }

    @Test
    void testPublicar_Sucesso() {
        Usuario usuarioMock = mock(Usuario.class);
        Consumidor consumidorMock = mock(Consumidor.class);
        Produto produtoMock = mock(Produto.class);
        Avaliacao avaliacaoMock = mock(Avaliacao.class);
        UsuarioDetalhes usuarioDetalhesMock = mock(UsuarioDetalhes.class);

        when(usuarioMock.getConsumidor()).thenReturn(consumidorMock);
        when(usuarioDetalhesMock.getId()).thenReturn(1L);
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(usuarioDetalhesMock);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoMock));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacaoMock);

        AvaliacaoRequestDTO avaliacaoRequestDTO = new AvaliacaoRequestDTO();
        avaliacaoRequestDTO.setProduto(1L);

        Avaliacao result = avaliacaoService.publicar(avaliacaoRequestDTO);

        assertNotNull(result);
        verify(usuarioRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(avaliacaoRepository).save(any(Avaliacao.class));
    }
}