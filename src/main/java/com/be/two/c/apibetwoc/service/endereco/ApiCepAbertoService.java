package com.be.two.c.apibetwoc.service.endereco;

import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.util.ApiCepAberto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiCepAbertoService implements IEndereco {

    private final ApiCepAberto apiCepAberto;

    @Override
    public Endereco returnAddressWithLatitudeAndLongitude(Endereco address) {
        Optional<ApiCepAberto.Cep> cepOpt = apiCepAberto.searchByCep(address.getCep());
        if (cepOpt.isEmpty()) throw new EntidadeNaoExisteException("O Cep pesquisado não pôde ser encontrado");
        ApiCepAberto.Cep info = cepOpt.get();
        address.setGeolocalizacaoX(info.getLongitude());
        address.setGeolocalizacaoY(info.getLatitude());
        return address;
    }
}
