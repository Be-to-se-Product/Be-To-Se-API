package com.be.two.c.apibetwoc.service.consumidor;

import com.be.two.c.apibetwoc.controller.consumidor.dto.ConsumidorCriacaoDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.service.consumidor.exception.ConsumidorConflitoException;
import com.be.two.c.apibetwoc.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsumidorServiceTest {

    @Mock
    private ConsumidorRepository consumidorRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ConsumidorService consumidorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCadastroConsumidor() {
        ConsumidorCriacaoDto consumidorDto = new ConsumidorCriacaoDto();
        consumidorDto.setNome("Nome");
        consumidorDto.setCpf("12345678901");
        consumidorDto.setCelular("123456789");
        consumidorDto.setGenero("Masculino");
        consumidorDto.setDataNascimento(LocalDate.of(2000, 1, 1));

        when(consumidorRepository.existsByCelular(anyString())).thenReturn(false);
        when(consumidorRepository.existsByCpf(anyString())).thenReturn(false);
        when(usuarioService.cadastrar(any(), any())).thenReturn(new Usuario());
        when(consumidorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Consumidor consumidorSalvo = consumidorService.cadastrar(consumidorDto);

        assertNotNull(consumidorSalvo);
        assertEquals("Nome", consumidorSalvo.getNome());
        assertEquals("12345678901", consumidorSalvo.getCpf());
        assertEquals("123456789", consumidorSalvo.getCelular());
        assertEquals("Masculino", consumidorSalvo.getGenero());
        assertEquals(LocalDate.of(2000, 1, 1), consumidorSalvo.getDataNascimento());
    }

    @Test
    void testCadastroConsumidorExistente() {
        ConsumidorCriacaoDto consumidorDto = new ConsumidorCriacaoDto();
        consumidorDto.setNome("Nome");
        consumidorDto.setCpf("12345678901");
        consumidorDto.setCelular("123456789");
        consumidorDto.setGenero("Masculino");
        consumidorDto.setDataNascimento(LocalDate.of(2000, 1, 1));

        when(consumidorRepository.existsByCelular(anyString())).thenReturn(true);

        assertThrows(ConsumidorConflitoException.class, () -> consumidorService.cadastrar(consumidorDto));
    }

    @Test
    void testAtualizarConsumidor() {
        Consumidor consumidorAtualizado = new Consumidor();
        consumidorAtualizado.setId(1L);
        consumidorAtualizado.setNome("Novo Nome");
        consumidorAtualizado.setCpf("12345678901");
        consumidorAtualizado.setCelular("123456789");
        consumidorAtualizado.setGenero("Masculino");
        consumidorAtualizado.setDataNascimento(LocalDate.of(2000, 1, 1));

        Usuario usuario = new Usuario();
        usuario.setConsumidor(consumidorAtualizado);
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);

        Consumidor consumidorExistente = new Consumidor();
        consumidorExistente.setId(1L);
        when(consumidorRepository.findById(1L)).thenReturn(Optional.of(consumidorExistente));

        when(consumidorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Consumidor consumidorAtualizadoResultado = consumidorService.atualizar(consumidorAtualizado, 1L);

        assertNotNull(consumidorAtualizadoResultado);
        assertEquals("Novo Nome", consumidorAtualizadoResultado.getNome());
        assertEquals("12345678901", consumidorAtualizadoResultado.getCpf());
        assertEquals("123456789", consumidorAtualizadoResultado.getCelular());
        assertEquals("Masculino", consumidorAtualizadoResultado.getGenero());
        assertEquals(LocalDate.of(2000, 1, 1), consumidorAtualizadoResultado.getDataNascimento());
    }

    @Test
    void testExisteConsumidorCaminhoFeliz() {
        Consumidor consumidorExistente = new Consumidor();
        consumidorExistente.setId(1L);

        when(consumidorRepository.findById(1L)).thenReturn(Optional.of(consumidorExistente));

        Consumidor consumidorEncontrado = consumidorService.existeConsumidor(1L);

        assertNotNull(consumidorEncontrado);
        assertEquals(1L, consumidorEncontrado.getId());
    }

    @Test
    void testExisteConsumidorNaoExiste() {
        when(consumidorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoExisteException.class, () -> consumidorService.existeConsumidor(1L));
    }
}