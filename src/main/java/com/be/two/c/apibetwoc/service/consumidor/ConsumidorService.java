package com.be.two.c.apibetwoc.service.consumidor;

import com.be.two.c.apibetwoc.controller.consumidor.dto.ConsumidorCriacaoDto;
import com.be.two.c.apibetwoc.controller.consumidor.dto.ConsumidorResponseDto;
import com.be.two.c.apibetwoc.controller.consumidor.mapper.ConsumidorMapper;
import com.be.two.c.apibetwoc.controller.consumidor.dto.ResponseConsumidorDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.TipoUsuario;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.service.InteresseService;
import com.be.two.c.apibetwoc.service.usuario.UsuarioService;
import com.be.two.c.apibetwoc.service.consumidor.exception.ConsumidorConflitoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumidorService {

    private final ConsumidorRepository consumidorRepository;
    private final UsuarioService usuarioService;
    private final InteresseService interesseService;

    public Consumidor cadastrar(ConsumidorCriacaoDto consumidorCriacaoDto) {

        if(consumidorRepository.existsByCelular(consumidorCriacaoDto.getCelular())) throw new ConsumidorConflitoException("Esse telefone esta em uso");

        if(consumidorRepository.existsByCpf(consumidorCriacaoDto.getCpf())) throw new ConsumidorConflitoException("Esse CPF já esta em uso");


        Usuario usuario = usuarioService.cadastrar(consumidorCriacaoDto.getUsuario(),TipoUsuario.CONSUMIDOR);
        Consumidor consumidor = ConsumidorMapper.of(consumidorCriacaoDto);
        consumidor.setUsuario(usuario);
        Consumidor consumidorSalvo = consumidorRepository.save(consumidor);
        return consumidorSalvo;
    }

    public Consumidor existeConsumidor(Long id){
        return consumidorRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("O consumidor procurado não existe")
        );
    }


    public Consumidor atualizar(Consumidor consumidor, Long id){
        Usuario usuario = usuarioService.buscarPorId(id);
        Consumidor c = existeConsumidor(usuario.getConsumidor().getId());
        consumidor.setUsuario(usuario);
        consumidor.setDataUltimaCompra(c.getDataUltimaCompra());
        consumidor.setImagem(c.getImagem());
        c = consumidorRepository.save(consumidor);
        return c;
    }

    public void excluir(Long id) {
        if (!consumidorRepository.existsById(id)) {
            throw new EntidadeNaoExisteException("O consumidor procurado não existe.");
        }
        Consumidor consumidor = consumidorRepository.getReferenceById(id);
        consumidor.setIsAtivo(false);
        consumidorRepository.save(consumidor);
    }
    public ConsumidorResponseDto buscarPorId(Long id) {
        return consumidorRepository
                .findById(id)
                .map(ConsumidorMapper::of)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum consumidor com esse id"));
    }

}
