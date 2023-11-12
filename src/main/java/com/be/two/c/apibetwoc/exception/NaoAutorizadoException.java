package com.be.two.c.apibetwoc.exception;

public class NaoAutorizadoException extends RuntimeException {
    public NaoAutorizadoException() {
        super("O usuário não está autorizado a fazer essa ação: " );
    }
}
