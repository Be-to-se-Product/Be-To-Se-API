package com.be.two.c.apibetwoc.infra;

public class EntidadeNaoExisteException extends RuntimeException {

    public EntidadeNaoExisteException(String message) {
        super(message);
    }
}
