package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.exception.NaoAutorizadoException;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);
        if(usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("O email %s não foi encontrado",username));
        }

        return new UsuarioDetalhes(usuarioOpt.get());
    }


    public UsuarioDetalhes loadUsuarioDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication: "+authentication.getPrincipal());
        return (UsuarioDetalhes) authentication.getPrincipal();
    }

    public void validarEstabelecimentoComerciante(Long idEstabelecimento){
        Long idUsuario = loadUsuarioDetails().getId();
        System.out.println("idUsuario: "+idUsuario);
        boolean isPermited =  usuarioRepository.existsByIdAndComercianteEstabelecimentoId(idUsuario,idEstabelecimento);
        System.out.println("isPermited: "+isPermited);
        if(!isPermited){
            throw new NaoAutorizadoException();
        }
    }
}
