package com.be.two.c.apibetwoc.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Exception {
    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail produtoNaoEncontrado(NoSuchElementException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Produto não encontrado");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail cadastroInvalido(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Json inválido");
        problemDetail.setDetail(exception.getMessage());

        if(exception.getMessage().contains("nome")){
            problemDetail.setDetail("O nome do produto é obrigatório");
        }

        return problemDetail;
    }

}
