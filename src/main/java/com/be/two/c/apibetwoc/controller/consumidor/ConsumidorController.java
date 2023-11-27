package com.be.two.c.apibetwoc.controller.consumidor;
import com.be.two.c.apibetwoc.controller.consumidor.dto.ConsumidorCriacaoDto;
import com.be.two.c.apibetwoc.controller.consumidor.dto.ResponseConsumidorDto;
import com.be.two.c.apibetwoc.controller.consumidor.mapper.ConsumidorMapper;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioLoginDTO;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioTokenDTO;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.service.AutenticacaoService;
import com.be.two.c.apibetwoc.service.consumidor.ConsumidorService;
import com.be.two.c.apibetwoc.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("consumidores")
@RequiredArgsConstructor
public class ConsumidorController {

    private final ConsumidorService consumidorService;
   private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ResponseConsumidorDto> cadastrar(@Valid @RequestBody ConsumidorCriacaoDto consumidorCriacaoDto,
                                                           UriComponentsBuilder uriBuilder) {
        Consumidor consumidor = consumidorService.cadastrar(consumidorCriacaoDto);
        UsuarioTokenDTO usuario =  usuarioService.autenticar(new UsuarioLoginDTO(consumidorCriacaoDto.getUsuario().email(),consumidorCriacaoDto.getUsuario().senha()));
        URI uri = uriBuilder.path("{id}").buildAndExpand(consumidor.getId()).toUri();
        return ResponseEntity.created(uri).body(ConsumidorMapper.of(consumidor,usuario));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> atualizar(@Valid @RequestBody ConsumidorCriacaoDto consumidor, @PathVariable Long id){
        Consumidor consumidorAtualizado = ConsumidorMapper.of(consumidor);
        consumidorService.atualizar(consumidorAtualizado,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        consumidorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
