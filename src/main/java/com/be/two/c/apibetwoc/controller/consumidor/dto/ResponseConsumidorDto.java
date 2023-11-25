package com.be.two.c.apibetwoc.controller.consumidor.dto;

import com.be.two.c.apibetwoc.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseConsumidorDto{
      private String token;
      private TipoUsuario tipoUsuario;
}
