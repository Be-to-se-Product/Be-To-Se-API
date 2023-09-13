package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public Long calcularRotaPessoa(@RequestBody CoordenadaDto coordenadaDto) {
        Pessoa pessoa = new Pessoa();

        return pessoa.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaBicicleta(@RequestBody CoordenadaDto coordenadaDto) {
        Bicicleta bicleta = new Bicicleta();

        return bicleta.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaCarro(@RequestBody CoordenadaDto coordenadaDto) {
        Carro carro = new Carro();

        return carro.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

}
