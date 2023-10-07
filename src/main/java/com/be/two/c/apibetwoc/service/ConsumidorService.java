package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.consumidor.ConsumidorCriacaoDto;
import com.be.two.c.apibetwoc.dto.consumidor.ConsumidorMapper;
import com.be.two.c.apibetwoc.dto.consumidor.ResponseConsumidorDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumidorService {

    private final ConsumidorRepository consumidorRepository;
    private final UsuarioService usuarioService;
    private final InteresseService interesseService;
    public ResponseConsumidorDto cadastrar(ConsumidorCriacaoDto consumidorCriacaoDto) {
       Usuario usuario = usuarioService.cadastrar(consumidorCriacaoDto.getUsuarioCriacaoDTO());

        Consumidor consumidor = ConsumidorMapper.of(consumidorCriacaoDto);
        consumidor.setUsuario(usuario);
        Consumidor consumidorSalvo = consumidorRepository.save(consumidor);
        interesseService.cadastrar(consumidorSalvo.getId(), consumidorCriacaoDto.getInteresses());
        return ConsumidorMapper.of(consumidorSalvo);
    }

    public List<ResponseConsumidorDto> listar() {
        final List<Consumidor> consumidores = consumidorRepository.findAll();
        return consumidores.stream()
                .map(ConsumidorMapper::of).toList();
    }

    public ResponseConsumidorDto buscarPorId(Long id) {
        return consumidorRepository
                .findById(id)
                .map(ConsumidorMapper::of)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum consumidor com esse id"));
    }
}
