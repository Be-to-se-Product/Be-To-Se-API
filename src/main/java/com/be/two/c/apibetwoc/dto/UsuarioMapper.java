package com.be.two.c.apibetwoc.dto;

import com.be.two.c.apibetwoc.model.Usuario;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDTO usuarioCriacaoDTO){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioCriacaoDTO.email());
        usuario.setSenha(usuarioCriacaoDTO.senha());
        usuario.setTipoUsuario(usuarioCriacaoDTO.tipoUsuario());
        return usuario;
    }

    public static UsuarioTokenDTO of(Usuario usuario, String token){
        UsuarioTokenDTO usuarioTokenDTO = new UsuarioTokenDTO();

        usuarioTokenDTO.setUserId(usuario.getId());
        usuarioTokenDTO.setEmail(usuario.getEmail());
        usuarioTokenDTO.setTipoUsuario(usuario.getTipoUsuario());
        usuarioTokenDTO.setToken(token);
        return usuarioTokenDTO;
    }
}
