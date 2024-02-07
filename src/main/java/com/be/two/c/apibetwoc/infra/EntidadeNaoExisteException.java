package com.be.two.c.apibetwoc.infra;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntidadeNaoExisteException extends RuntimeException {

    public EntidadeNaoExisteException(String message) {
        super(message);
    }

}
