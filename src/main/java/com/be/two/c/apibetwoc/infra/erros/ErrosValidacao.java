package com.be.two.c.apibetwoc.infra.erros;

import org.springframework.validation.FieldError;

public record ErrosValidacao(String campo,
                             String mensagem) {
    public ErrosValidacao(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
