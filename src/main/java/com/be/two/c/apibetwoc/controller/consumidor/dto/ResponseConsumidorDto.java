package com.be.two.c.apibetwoc.controller.consumidor.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseConsumidorDto{
        private Long id;
        private String nome;
        private String email;
        private String genero;
        private String celular;
        private LocalDate dataNascimento;
        private String cpf;
}
