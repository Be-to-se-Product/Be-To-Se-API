package com.be.two.c.apibetwoc.service.consumidor.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConsumidorConflitoException extends RuntimeException {

    public ConsumidorConflitoException(String message) {
        super(message);
    }
}
