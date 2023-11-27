package com.be.two.c.apibetwoc.controller.comerciante.mapper;

import com.be.two.c.apibetwoc.controller.comerciante.dto.*;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Endereco;

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
    public static Comerciante of(ComercianteAtualizarDTO dto){
        Comerciante comerciante = new Comerciante();
        comerciante.setNome(dto.getNome());
        comerciante.setCnpj(dto.getCnpj());
        comerciante.setDataUltimoAcesso(LocalDate.now());
        comerciante.setRazaoSocial(dto.getRazaoSocial());
        return comerciante;
    }
    public static ResponseComercianteDto of(Comerciante comerciante){
        ResponseComercianteDto responseComercianteDto = new ResponseComercianteDto();
        responseComercianteDto.setId(comerciante.getId());
        responseComercianteDto.setNome(comerciante.getNome());
        responseComercianteDto.setEmail(comerciante.getUsuario().getEmail());
        return responseComercianteDto;
    }

    public static ComercianteResponseDTO toComercianteResponseDTO(Comerciante comerciante){
        ComercianteResponseDTO comercianteResponseDTO = new ComercianteResponseDTO();
        comercianteResponseDTO.setCnpj(comerciante.getCnpj());
        comercianteResponseDTO.setNome(comerciante.getNome());
        comercianteResponseDTO.setRazaoSocial(comerciante.getRazaoSocial());
        comercianteResponseDTO.setEmail(comerciante.getUsuario().getEmail());
        comercianteResponseDTO.setEndereco(toComercianteEnderecoResponse(comerciante.getEndereco()));
        return comercianteResponseDTO;
    }

    public static ComercianteEnderecoResponseDTO toComercianteEnderecoResponse(Endereco endereco){
        ComercianteEnderecoResponseDTO comercianteEnderecoResponseDTO = new ComercianteEnderecoResponseDTO();
        comercianteEnderecoResponseDTO.setCep(endereco.getCep());
        comercianteEnderecoResponseDTO.setNumero(endereco.getNumero());
        comercianteEnderecoResponseDTO.setBairro(endereco.getBairro());
        comercianteEnderecoResponseDTO.setRua(endereco.getRua());
        return comercianteEnderecoResponseDTO;
    }

}