package com.be.two.c.apibetwoc.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbidenPedidoException extends RuntimeException{
    public ForbidenPedidoException(String s) {
    }
}
