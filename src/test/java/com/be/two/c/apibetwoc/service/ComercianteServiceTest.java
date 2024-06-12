package com.be.two.c.apibetwoc.service;

import static org.junit.jupiter.api.Assertions.*;

import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteCriacaoDto;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.TipoUsuario;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.ComercianteRepository;
import com.be.two.c.apibetwoc.service.endereco.EnderecoService;
import com.be.two.c.apibetwoc.service.usuario.UsuarioService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;

class ComercianteServiceTest {

    private ComercianteService comercianteService;
    private ComercianteRepository comercianteRepository;
    private UsuarioService usuarioService;
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        comercianteRepository = mock(ComercianteRepository.class);
        usuarioService = mock(UsuarioService.class);
        enderecoService = mock(EnderecoService.class);
        comercianteService = new ComercianteService(comercianteRepository, enderecoService, usuarioService);
    }

    @Test
    void testCadastrar() {
        ComercianteCriacaoDto dto = new ComercianteCriacaoDto();
        dto.setNome("Nome do Comerciante");
        dto.setCnpj("123456789");
        dto.setRazaoSocial("Razão Social");
        dto.setUsuarioCriacaoDTO(new UsuarioCriacaoDTO("comerciante@example.com", "senha"));
        dto.setCep("12345-678");

        Usuario usuarioCriado = new Usuario();
        Endereco enderecoCriado = new Endereco();
        when(usuarioService.cadastrar(any(), eq(TipoUsuario.COMERCIANTE))).thenReturn(usuarioCriado);
        when(enderecoService.cadastrar(eq(dto.getCep()), isNull())).thenReturn(enderecoCriado);

        when(comercianteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Comerciante comercianteCriado = comercianteService.cadastrar(dto);

        verify(usuarioService).cadastrar(any(), eq(TipoUsuario.COMERCIANTE));
        verify(enderecoService).cadastrar(eq(dto.getCep()), isNull());

        verify(comercianteRepository).save(comercianteCriado);

        assertEquals(usuarioCriado, comercianteCriado.getUsuario());
        assertEquals(enderecoCriado, comercianteCriado.getEndereco());
    }

    @Test
    void testBuscarPorId() {
        Long comercianteId = 1L;
        Comerciante comercianteFicticio = new Comerciante();
        comercianteFicticio.setId(comercianteId);

        when(comercianteRepository.findById(comercianteId)).thenReturn(java.util.Optional.of(comercianteFicticio));

        Comerciante comercianteEncontrado = comercianteService.buscarPorId(comercianteId);

        assertEquals(comercianteFicticio, comercianteEncontrado);
    }

    @Test
    void testBuscarPorId_Excecao() {
        Long comercianteId = 1L;
        when(comercianteRepository.findById(comercianteId)).thenThrow(new EntidadeNaoExisteException("Comerciante não encontrado"));
        assertThrows(EntidadeNaoExisteException.class, () -> comercianteService.buscarPorId(comercianteId));
    }
}
