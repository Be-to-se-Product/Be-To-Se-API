package com.be.two.c.apibetwoc.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;


public enum StatusPedido {
    AGUARDANDO_RETIRADA, ENTREGUE, PENDENTE, PREPARO,CANCELADO
}
