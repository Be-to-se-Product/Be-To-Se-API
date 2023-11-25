package com.be.two.c.apibetwoc.controller.consumidor.mapper;

import com.be.two.c.apibetwoc.controller.consumidor.dto.ConsumidorCriacaoDto;
import com.be.two.c.apibetwoc.controller.consumidor.dto.ResponseConsumidorDto;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioTokenDTO;
import com.be.two.c.apibetwoc.model.Consumidor;

import java.time.LocalDateTime;

public class ConsumidorMapper {

    public static Consumidor of(ConsumidorCriacaoDto consumidorCriacaoDto){
        Consumidor consumidor = new Consumidor();

        consumidor.setNome(consumidorCriacaoDto.getNome());
        consumidor.setCpf(consumidorCriacaoDto.getCpf());
        consumidor.setCelular(consumidorCriacaoDto.getCelular());
        consumidor.setGenero(consumidorCriacaoDto.getGenero());
        consumidor.setDataCriacao(LocalDateTime.now());
        consumidor.setDataNascimento(consumidorCriacaoDto.getDataNascimento());
        return consumidor;
    }

    public static ResponseConsumidorDto of(Consumidor consumidor, UsuarioTokenDTO usuario){
       ResponseConsumidorDto consumidorDto = new ResponseConsumidorDto();
       consumidorDto.setId(usuario.getId());
       consumidorDto.setNome(consumidor.getNome());
       consumidorDto.setToken(usuario.getToken());
       consumidorDto.setTipoUsuario(consumidor.getUsuario().getTipoUsuario());
      return consumidorDto;
    }
}
