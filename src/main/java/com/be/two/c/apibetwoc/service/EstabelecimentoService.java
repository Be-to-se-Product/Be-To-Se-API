package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.CadastroEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
    public Estabelecimento cadastroEstabelecimento(@RequestBody CadastroEstabelecimentoDto estabelecimento){
        return estabelecimentoRepository.save(new Estabelecimento(null,estabelecimento.getNome(),estabelecimento.getSegmento(),estabelecimento.getDataCriacao(),estabelecimento.getTelefoneContato(),estabelecimento.getEnquadramentoJuridico(),estabelecimento.getReferenciaInstagram(),estabelecimento.getReferenciaFacebook(),estabelecimento.getEmailContato(),estabelecimento.getIsAtivo(),estabelecimento.getComerciante(),estabelecimento.getEndereco()));
    }
    public List<Estabelecimento> listarTodos(){
        List<Estabelecimento> lista = estabelecimentoRepository.findAll();
        return lista;
    }
    public ResponseEntity<Estabelecimento> atualizar(@RequestBody CadastroEstabelecimentoDto estabelecimentoDto, @PathVariable Long id){
        Estabelecimento cadastravel= new Estabelecimento(id,estabelecimentoDto.getNome(),estabelecimentoDto.getSegmento(),estabelecimentoDto.getDataCriacao(),estabelecimentoDto.getTelefoneContato(),estabelecimentoDto.getEnquadramentoJuridico(),estabelecimentoDto.getReferenciaInstagram(),estabelecimentoDto.getReferenciaFacebook(),estabelecimentoDto.getEmailContato(),estabelecimentoDto.getIsAtivo(),estabelecimentoDto.getComerciante(),estabelecimentoDto.getEndereco());
        if (this.estabelecimentoRepository.existsById(id)){
            estabelecimentoRepository.save(cadastravel);
            return ResponseEntity.ok(cadastravel);
        };

        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<Estabelecimento> deletar(@PathVariable Long id){
        if (this.estabelecimentoRepository.existsById(id)){
            estabelecimentoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    public Long calcularRotaPessoa(@RequestBody CoordenadaDto coordenadaDto) {
        Pessoa pessoa = new Pessoa();

        return pessoa.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaBicicleta(@RequestBody CoordenadaDto coordenadaDto) {
        Bicicleta bicleta = new Bicicleta();

        return bicleta.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaCarro(@RequestBody CoordenadaDto coordenadaDto) {
        Carro carro = new Carro();

        return carro.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

}
