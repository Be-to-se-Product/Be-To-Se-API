package com.be.two.c.apibetwoc.service;

import static org.mockito.Mockito.*;

import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoAtualizarDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoEnderecoCadastroDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoSecaoAtualizarDTO;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstabelecimentoServiceTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;
    @Mock
    private ComercianteRepository comercianteRepository;
    @Mock
    private MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private AgendaService agendaService;
    @Mock
    private SecaoRepository secaoRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private AutenticacaoService autenticacaoService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ImagemService imagemService;
    @Mock
    private ImagemRepository imagemRepository;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    @Mock
    private MetodoPagamentoRepository metodoPagamentoRepository;

    @InjectMocks
    private EstabelecimentoService estabelecimentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCadastroEstabelecimento() {
        EstabelecimentoCadastroDTO estabelecimentoDTO = getEstabelecimentoCadastroDTO();

        UsuarioDetalhes usuarioDetalhesMock = Mockito.mock(UsuarioDetalhes.class);
        when(usuarioDetalhesMock.getId()).thenReturn(1L);
        when(autenticacaoService.loadUsuarioDetails()).thenReturn(usuarioDetalhesMock);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        Comerciante comercianteMock = new Comerciante();
        comercianteMock.setId(1L);
        usuarioMock.setComerciante(comercianteMock);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(comercianteRepository.findById(1L)).thenReturn(Optional.of(comercianteMock));
        when(enderecoService.cadastrar(anyString(), anyString())).thenReturn(new Endereco());

        Estabelecimento estabelecimentoCriado = estabelecimentoService.cadastroEstabelecimento(estabelecimentoDTO);

        verify(estabelecimentoRepository).save(any(Estabelecimento.class));
    }

    @NotNull
    private static EstabelecimentoCadastroDTO getEstabelecimentoCadastroDTO() {
        EstabelecimentoEnderecoCadastroDTO enderecoDTO = new EstabelecimentoEnderecoCadastroDTO();
        enderecoDTO.setCep("12345678");
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setBairro("Bairro Teste");
        enderecoDTO.setNumero("123");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setUf("UF");

        EstabelecimentoCadastroDTO estabelecimentoDTO = new EstabelecimentoCadastroDTO();
        estabelecimentoDTO.setEndereco(enderecoDTO);
        estabelecimentoDTO.setNome("Nome Teste");
        estabelecimentoDTO.setCnpj("00.000.000/0000-00");
        estabelecimentoDTO.setTelefoneContato("123456789");
        estabelecimentoDTO.setEmailContato("teste@teste.com");
        estabelecimentoDTO.setReferenciaInstagram("instagramTeste");
        estabelecimentoDTO.setReferenciaFacebook("facebookTeste");
        estabelecimentoDTO.setAgenda(new ArrayList<>());
        estabelecimentoDTO.setSegmento("Segmento Teste");
        estabelecimentoDTO.setMetodoPagamento(new ArrayList<>());
        estabelecimentoDTO.setSecao(new ArrayList<>(List.of("Secao Teste")));
        return estabelecimentoDTO;
    }

    @Test
    void testAtualizarEstabelecimento() {
        Long estabelecimentoId = 1L;

        EstabelecimentoAtualizarDTO estabelecimentoDto = getEstabelecimentoAtualizarDTO();

        Estabelecimento estabelecimentoMock = new Estabelecimento();
        estabelecimentoMock.setId(estabelecimentoId);

        Endereco enderecoMock = new Endereco();
        enderecoMock.setEstabelecimento(List.of(estabelecimentoMock));

        when(estabelecimentoRepository.findById(estabelecimentoId)).thenReturn(Optional.of(estabelecimentoMock));
        when(enderecoRepository.findByEstabelecimentoId(estabelecimentoId)).thenReturn(enderecoMock);
        when(metodoPagamentoAceitoRepository.findByEstabelecimentoId(estabelecimentoId)).thenReturn(new ArrayList<>());
        when(metodoPagamentoRepository.findByIdIn(anyList())).thenReturn(new ArrayList<>());

        // Mock the save method to return the saved Estabelecimento
        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estabelecimento estabelecimentoAtualizado = estabelecimentoService.atualizarEstabelecimento(estabelecimentoDto, estabelecimentoId);

        verify(estabelecimentoRepository).save(estabelecimentoMock);
        verify(metodoPagamentoAceitoRepository).saveAll(anyList());
        verify(enderecoRepository).save(enderecoMock);
        verify(secaoRepository).saveAll(anyList());
    }

    @NotNull
    private static EstabelecimentoAtualizarDTO getEstabelecimentoAtualizarDTO() {
        EstabelecimentoEnderecoCadastroDTO enderecoDTO = new EstabelecimentoEnderecoCadastroDTO();
        enderecoDTO.setCep("12345678");
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setBairro("Bairro Teste");
        enderecoDTO.setNumero("123");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setUf("UF");

        EstabelecimentoAtualizarDTO estabelecimentoDto = new EstabelecimentoAtualizarDTO();
        estabelecimentoDto.setEndereco(enderecoDTO);
        estabelecimentoDto.setNome("Nome Teste Atualizado");
        estabelecimentoDto.setSegmento("Segmento Teste Atualizado");
        estabelecimentoDto.setTelefoneContato("987654321");
        estabelecimentoDto.setEmailContato("atualizado@teste.com");
        estabelecimentoDto.setReferenciaInstagram("instagramAtualizado");
        estabelecimentoDto.setReferenciaFacebook("facebookAtualizado");
        estabelecimentoDto.setAgenda(new ArrayList<>());
        estabelecimentoDto.setMetodoPagamento(new ArrayList<>());
        estabelecimentoDto.setSecao(new ArrayList<>(List.of(new EstabelecimentoSecaoAtualizarDTO())));
        return estabelecimentoDto;
    }

    @Test
    void testDeletarEstabelecimentoExistente() {
        // Arrange
        Long estabelecimentoId = 1L;
        Estabelecimento estabelecimentoMock = new Estabelecimento();
        estabelecimentoMock.setId(estabelecimentoId);

        when(estabelecimentoRepository.existsById(estabelecimentoId)).thenReturn(true);
        when(estabelecimentoRepository.getReferenceById(estabelecimentoId)).thenReturn(estabelecimentoMock);

        // Act
        estabelecimentoService.deletar(estabelecimentoId);

        // Assert
        verify(estabelecimentoRepository).existsById(estabelecimentoId);
        verify(estabelecimentoRepository).getReferenceById(estabelecimentoId);
        verify(estabelecimentoRepository).save(estabelecimentoMock);
        Assertions.assertFalse(estabelecimentoMock.getIsAtivo());
    }

    @Test
    void testDeletarEstabelecimentoNaoExistente() {
        // Arrange
        Long estabelecimentoId = 1L;

        when(estabelecimentoRepository.existsById(estabelecimentoId)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(EntidadeNaoExisteException.class, () -> estabelecimentoService.deletar(estabelecimentoId));

        verify(estabelecimentoRepository).existsById(estabelecimentoId);
        verify(estabelecimentoRepository, never()).getReferenceById(estabelecimentoId);
        verify(estabelecimentoRepository, never()).save(any(Estabelecimento.class));
    }
}

