package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.UsuarioCriacaoDTO;
import com.be.two.c.apibetwoc.dto.UsuarioLoginDTO;
import com.be.two.c.apibetwoc.dto.UsuarioTokenDTO;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioCriacaoDTO usuarioCriacaoDTO, UriComponentsBuilder uriBuilder){

        Usuario usuario = usuarioService.cadastrar(usuarioCriacaoDTO);
        URI uri = uriBuilder.path("usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
         return ResponseEntity.created(uri).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDTO> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO){
        UsuarioTokenDTO usuarioToken = usuarioService.autenticar(usuarioLoginDTO);
        return ResponseEntity.ok(usuarioToken);
    }
}
