package com.be.two.c.apibetwoc.service.endereco;

import com.be.two.c.apibetwoc.model.Endereco;

import java.io.IOException;

public interface IEndereco {
    public Endereco returnAddressWithLatitudeAndLongitude(Endereco address) throws IOException;
}
