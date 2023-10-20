package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);

        if(usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("O email %s não foi encontrado",username));
        }

        return new UsuarioDetalhes(usuarioOpt.get());
    }
}
