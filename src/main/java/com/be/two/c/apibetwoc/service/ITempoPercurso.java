package com.be.two.c.apibetwoc.service;

import java.sql.Time;

public interface ITempoPercurso {
    Long calcularTempoDeslocamento(Double x, Double y, Double toX, Double toY);
}
