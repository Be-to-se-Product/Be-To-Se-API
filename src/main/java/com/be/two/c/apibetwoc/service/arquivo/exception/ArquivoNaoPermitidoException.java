package com.be.two.c.apibetwoc.service.arquivo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArquivoNaoPermitidoException extends RuntimeException{

    public ArquivoNaoPermitidoException(){
        super("Arquivo não permitido");
    }
}
