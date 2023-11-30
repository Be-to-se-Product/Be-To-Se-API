package com.be.two.c.apibetwoc.controller.comerciante;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteAtualizarDTO;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteCriacaoDto;

import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteResponseDTO;
import com.be.two.c.apibetwoc.controller.comerciante.mapper.ComercianteMapper;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ResponseComercianteDto;

import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.service.ComercianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comerciantes")
@RequiredArgsConstructor
public class ComercianteController {

    private final ComercianteService comercianteService;

    @PostMapping
    public ResponseEntity<ResponseComercianteDto> cadastrar(@RequestBody @Valid ComercianteCriacaoDto comercianteCriacaoDto){
        Comerciante comerciante = comercianteService.cadastrar(comercianteCriacaoDto);
        ResponseComercianteDto responseComercianteDto = ComercianteMapper.of(comerciante);
        URI uri = UriComponentsBuilder.fromPath("{id}").buildAndExpand(responseComercianteDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseComercianteDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseComercianteDto> editar(@RequestBody @Valid ComercianteAtualizarDTO comercianteAtualizarDTO, @PathVariable Long id){
        Comerciante comerciante = comercianteService.editar(comercianteAtualizarDTO, id);
        ResponseComercianteDto responseComercianteDto = ComercianteMapper.of(comerciante);
        return ResponseEntity.ok(responseComercianteDto);
    }
    @GetMapping
    public ResponseEntity<List<ResponseComercianteDto>> listar(){
        List<ResponseComercianteDto> comerciantes = comercianteService.listar();
        if(comerciantes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comerciantes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ComercianteResponseDTO> comercianetPorId(@PathVariable Long id){
        Comerciante comerciante = comercianteService.buscarPorId(id);
        return ResponseEntity.ok(ComercianteMapper.toComercianteResponseDTO(comerciante));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        comercianteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
