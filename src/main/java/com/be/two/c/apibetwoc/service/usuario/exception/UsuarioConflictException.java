package com.be.two.c.apibetwoc.service.usuario.exception;


import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioConflictException extends RuntimeException{
    public UsuarioConflictException(String message) {
        super(message);
    }
}
