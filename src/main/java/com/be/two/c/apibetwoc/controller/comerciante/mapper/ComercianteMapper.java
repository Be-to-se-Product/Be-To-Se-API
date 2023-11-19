package com.be.two.c.apibetwoc.controller.comerciante.mapper;

import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteCriacaoDto;
import com.be.two.c.apibetwoc.controller.comerciante.dto.ResponseComercianteDto;
import com.be.two.c.apibetwoc.model.Comerciante;

import java.time.LocalDate;

public class ComercianteMapper {

    public static Comerciante of(ComercianteCriacaoDto comercianteCriacaoDto){
        Comerciante comerciante = new Comerciante();
        comerciante.setNome(comercianteCriacaoDto.getNome());
        comerciante.setCnpj(comercianteCriacaoDto.getCnpj());
        comerciante.setDataCriacao(LocalDate.now());
        comerciante.setDataUltimoAcesso(LocalDate.now());
        comerciante.setRazaoSocial(comercianteCriacaoDto.getRazaoSocial());
        return comerciante;
    }

    public static ResponseComercianteDto of(Comerciante comerciante){
        ResponseComercianteDto responseComercianteDto = new ResponseComercianteDto();
        responseComercianteDto.setId(comerciante.getId());
        responseComercianteDto.setNome(comerciante.getNome());
        responseComercianteDto.setEmail(comerciante.getUsuario().getEmail());
        return responseComercianteDto;
    }
}