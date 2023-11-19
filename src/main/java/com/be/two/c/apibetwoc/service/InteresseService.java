package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.consumidor.dto.InteresseCriacaoDto;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Consumidor;
import com.be.two.c.apibetwoc.model.Interesse;
import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.repository.ConsumidorRepository;
import com.be.two.c.apibetwoc.repository.InteresseRepository;
import com.be.two.c.apibetwoc.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteresseService {

    private final InteresseRepository interesseRepository;
    private final ConsumidorRepository consumidorRepository;
    private final TagRepository tagRepository;

    public List<Interesse> cadastrar(Long idConsumidor, List<InteresseCriacaoDto> interesseCriacaoDtos) {
        List<Interesse> interesses = new ArrayList<>();
        Consumidor consumidor = consumidorRepository.getReferenceById(idConsumidor);

        if (consumidor == null) {
            throw new EntidadeNaoExisteException("O consumidor informado não existe no banco de dados");
        }

        for (InteresseCriacaoDto interesseCriacaoDto : interesseCriacaoDtos) {
            Tag tag = tagRepository.findById(interesseCriacaoDto.getIdTag()).orElse(null);

            if (tag == null) {
                tag = new Tag();
                tag.setDescricao(interesseCriacaoDto.getDescricaoTag());
                tag = tagRepository.save(tag);
            }

            Interesse interesse = new Interesse();
            interesse.setConsumidor(consumidor);
            interesse.setTag(tag);

            Interesse interesseSalvo = interesseRepository.save(interesse);

            interesses.add(interesseSalvo);
        }

        return interesses;
    }

}
