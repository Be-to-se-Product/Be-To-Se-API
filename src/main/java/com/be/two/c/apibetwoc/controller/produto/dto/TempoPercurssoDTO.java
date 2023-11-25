package com.be.two.c.apibetwoc.controller.produto.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempoPercurssoDTO {
    private Long carro;
    private Long pessoa;
    private Long bicicleta;
}
