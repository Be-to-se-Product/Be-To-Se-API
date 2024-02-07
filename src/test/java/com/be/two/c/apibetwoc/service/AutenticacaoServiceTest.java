package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Retorna usuário detalhes quando existe")
    void testUsuarioDetalhesQuandoExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        UserDetails usuarioRetornado = new UsuarioDetalhes(usuario);

        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.of(usuario));

        UserDetails usuarioDetalhes = autenticacaoService.loadUserByUsername("teste@email.com");
        assertEquals(usuarioRetornado.getUsername(), usuarioDetalhes.getUsername());
    }

    @Test
    @DisplayName("Retorna UsernameNotFoundException quando não existe")
    void testUsernameNotFoundExceptionQuandoNaoExiste() {
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.empty());

        String emailProcurado = "teste@email.com";
        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> autenticacaoService.loadUserByUsername(emailProcurado));

        assertEquals("O email " + emailProcurado + " não foi encontrado", ex.getMessage());
    }
}