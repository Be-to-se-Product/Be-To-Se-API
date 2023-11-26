package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.repository.ComercianteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComercianteServiceTest {

    @InjectMocks
    private ComercianteService comercianteService;

    @Mock
    private ComercianteRepository comercianteRepository;

    @Test
    @DisplayName("Deve retornar comerciante certo quando existir")
    void deveRetornarComercianteCertoQuandoExistir(){
        Comerciante comerciante = new Comerciante();
        comerciante.setId(1L);
        comerciante.setNome("Comerciante 1");

        Mockito.when(comercianteRepository.findById(1L)).thenReturn(java.util.Optional.of(comerciante));

        Comerciante comercianteRetornado = comercianteService.buscarPorId(1L);

        assertEquals(comercianteRetornado.getId(), comerciante.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando comerciante não existir")
    void deveLancarExcecaoQuandoComercianteNaoExistir(){
        Mockito.when(comercianteRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        EntidadeNaoExisteException ex = assertThrows(EntidadeNaoExisteException.class, () -> comercianteService.buscarPorId(1L));
        assertEquals("Comerciante não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando comerciante não existir")
    void deveLancarExcecaoQuandoComercianteNaoExistirDeletar(){
        Mockito.when(comercianteRepository.existsById(1L)).thenReturn(false);

        EntidadeNaoExisteException ex = assertThrows(EntidadeNaoExisteException.class, () -> comercianteService.excluir(1L));
        assertEquals("Comerciante não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Deve alterar ativo para false quando comerciante existir")
    void deveAlterarAtivoParaFalseQuandoComercianteExistir(){
        Comerciante comerciante = new Comerciante();
        comerciante.setId(1L);
        comerciante.setNome("Comerciante 1");
        comerciante.setIsAtivo(true);

        Mockito.when(comercianteRepository.existsById(1L)).thenReturn(true);
        Mockito.when(comercianteRepository.getReferenceById(1L)).thenReturn(comerciante);

        comercianteService.excluir(1L);

        assertFalse(comerciante.getIsAtivo());
    }
}