package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.comerciante.ComercianteCriacaoDto;
import com.be.two.c.apibetwoc.dto.comerciante.ComercianteMapper;
import com.be.two.c.apibetwoc.dto.comerciante.ResponseComercianteDto;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.ComercianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComercianteService {

    private final ComercianteRepository comercianteRepository;
    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;

    public Comerciante cadastrar(ComercianteCriacaoDto comercianteCriacaoDto){
        Usuario usuario = usuarioService.cadastrar(comercianteCriacaoDto.getUsuarioCriacaoDTO());
        Endereco endereco = enderecoService.cadastrar(comercianteCriacaoDto.getCep());
        Comerciante comerciante = ComercianteMapper.of(comercianteCriacaoDto);
        comerciante.setUsuario(usuario);
        comerciante.setEndereco(endereco);

        return comercianteRepository.save(comerciante);
    }

    public List<ResponseComercianteDto> listar(){
        return comercianteRepository
                .findAll()
                .stream()
                .map(ComercianteMapper :: of)
                .toList();
    }

    
}
