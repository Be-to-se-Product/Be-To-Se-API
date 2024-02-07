package com.be.two.c.apibetwoc.controller.usuario;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioLoginDTO;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioTokenDTO;
import com.be.two.c.apibetwoc.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDTO> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO){
        UsuarioTokenDTO usuarioToken = usuarioService.autenticar(usuarioLoginDTO);
        return ResponseEntity.ok(usuarioToken);
    }
}
