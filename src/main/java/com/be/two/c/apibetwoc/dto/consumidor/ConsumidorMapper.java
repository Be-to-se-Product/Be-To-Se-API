package com.be.two.c.apibetwoc.dto.consumidor;

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

    public static ResponseConsumidorDto of(Consumidor consumidor){
       ResponseConsumidorDto consumidorDto = new ResponseConsumidorDto();
       consumidorDto.setId(consumidor.getId());
       consumidorDto.setEmail(consumidor.getUsuario().getEmail());
       consumidorDto.setNome(consumidor.getNome());
       consumidorDto.setGenero(consumidor.getGenero());
       consumidorDto.setCelular(consumidor.getCelular());
       consumidorDto.setDataNascimento(consumidor.getDataNascimento());
      return consumidorDto;
    }
}
