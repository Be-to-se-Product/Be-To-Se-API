package com.be.two.c.apibetwoc.controller.usuario.mapper;

import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioCriacaoDTO;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioTokenDTO;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Usuario;

import java.util.Optional;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDTO usuarioCriacaoDTO){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioCriacaoDTO.email());
        usuario.setSenha(usuarioCriacaoDTO.senha());
        return usuario;
    }

    public static UsuarioTokenDTO of(Usuario usuario, String token){
        UsuarioTokenDTO usuarioTokenDTO = new UsuarioTokenDTO();

        Optional<Comerciante> optComerciante = Optional.ofNullable(usuario.getComerciante());
        Optional<Consumidor> optConsumidor = Optional.ofNullable(usuario.getConsumidor());
        if(optComerciante.isPresent()) usuarioTokenDTO.setNome(optComerciante.get().getNome());
        if(optConsumidor.isPresent()) usuarioTokenDTO.setNome(optConsumidor.get().getNome());
        usuarioTokenDTO.setId(usuario.getId());
        usuarioTokenDTO.setEmail(usuario.getEmail());
        usuarioTokenDTO.setTipoUsuario(usuario.getTipoUsuario());
        usuarioTokenDTO.setToken(token);
        return usuarioTokenDTO;
    }
}
