package com.be.two.c.apibetwoc.service.endereco;

import com.be.two.c.apibetwoc.controller.comerciante.dto.ComercianteAtualizarDTO;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.Endereco;
import com.be.two.c.apibetwoc.repository.EnderecoRepository;
import com.be.two.c.apibetwoc.util.ApiCepAberto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final IEndereco addressIntegrationService;

    public Endereco cadastrar(String cep,String numero) {
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setNumero(numero);

        try {
        Endereco enderecoResolvido =   addressIntegrationService.returnAddressWithLatitudeAndLongitude(endereco);
            endereco.setGeolocalizacaoX(enderecoResolvido.getGeolocalizacaoX());
            endereco.setGeolocalizacaoY(enderecoResolvido.getGeolocalizacaoY());
            endereco.setNumero(numero);
            endereco.setCep(cep);
            return enderecoRepository.save(endereco);
        }

        catch (IOException e){
            throw new EntidadeNaoExisteException();
        }



    }

    public Endereco cadastrar(Endereco endereco) throws IOException {
        Endereco newEndereco = addressIntegrationService.returnAddressWithLatitudeAndLongitude(endereco);
        return enderecoRepository.save(newEndereco);
    }

    public Endereco editar(ComercianteAtualizarDTO dto,Long id) {
        if (Objects.nonNull(dto.getCep())) throw new EntidadeNaoExisteException("O Cep pesquisado não pôde ser encontrado");
        Endereco endereco = new Endereco();

        endereco.setBairro("");
        endereco.setCep(dto.getCep());
        endereco.setRua("");
        endereco.setGeolocalizacaoX(0);
        endereco.setGeolocalizacaoY(0);
        endereco.setNumero(dto.getNumero());
        endereco.setId(id);

        return enderecoRepository.save(endereco);
    }

    public Endereco editar(Endereco endereco, Long id) throws IOException {
        buscarPorId(id);
        Endereco updatedAddress = addressIntegrationService.returnAddressWithLatitudeAndLongitude(endereco);
        updatedAddress.setId(id);
        return enderecoRepository.save(updatedAddress);
    }

    public Endereco buscarPorId(Long id){
        return enderecoRepository.findById(id).orElseThrow(
                ()->new EntidadeNaoExisteException("Endereco não encontrado")
        );
    }


}