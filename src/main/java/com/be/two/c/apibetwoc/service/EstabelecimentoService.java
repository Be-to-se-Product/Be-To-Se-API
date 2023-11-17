package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.agenda.AgendaMapper;
import com.be.two.c.apibetwoc.dto.estabelecimento.AtualizarEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.estabelecimento.CadastroEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.CoordenadaDto;
import com.be.two.c.apibetwoc.dto.estabelecimento.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.dto.estabelecimento.ResponseEstabelecimentoDto;
import com.be.two.c.apibetwoc.dto.secao.SecaoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.AgendaRepository;
import com.be.two.c.apibetwoc.repository.ComercianteRepository;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import com.be.two.c.apibetwoc.repository.SecaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ComercianteRepository comercianteRepository;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final AgendaRepository agendaRepository;
    private final SecaoRepository secaoRepository;

    public Estabelecimento listarPorId(Long id){
        return estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));
    }

    public ResponseEstabelecimentoDto listarPorId2(Long id){
        Estabelecimento estabelecimento = estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));

        List<Agenda> agenda = agendaRepository.findByEstabelecimentoId(id);
        List<MetodoPagamentoAceito> metodos = metodoPagamentoAceitoService.findByEstabelecimentoId(id);

        return EstabelecimentoMapper.of(estabelecimento, agenda, metodos);
    }

    public List<Estabelecimento> listarTodos(){
        return estabelecimentoRepository.findAll();
    }

    public List<Estabelecimento> listarPorSegmento(String segmento){
        return estabelecimentoRepository.findBySegmento(segmento);
    }

    @Transactional
    public Estabelecimento cadastroEstabelecimento(CadastroEstabelecimentoDto cadastroEstabelecimentoDto){
        Comerciante comerciante = comercianteRepository
                .findById(cadastroEstabelecimentoDto.getIdComerciante())
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum comerciante com esse id"));

        Estabelecimento estabelecimento = EstabelecimentoMapper.of(cadastroEstabelecimentoDto, comerciante);
        Endereco endereco = EstabelecimentoMapper.of(cadastroEstabelecimentoDto.getEnderecoDto());
        estabelecimento.setEndereco(endereco);

        Estabelecimento estabelecimentoCriado = estabelecimentoRepository.save(estabelecimento);

        metodoPagamentoAceitoService.cadastrarMetodosPagamentos(estabelecimentoCriado, cadastroEstabelecimentoDto.getIdMetodoPagamento());

        agendaRepository.saveAll(AgendaMapper.of(cadastroEstabelecimentoDto.getAgenda(), estabelecimentoCriado));

        secaoRepository.saveAll(SecaoMapper.of(cadastroEstabelecimentoDto.getSecao(), estabelecimentoCriado));

        return estabelecimentoCriado;
    }

    public Estabelecimento atualizarEstabelecimento(AtualizarEstabelecimentoDto estabelecimentoDto, Long id){
        Estabelecimento estabelecimento = listarPorId(id);
        Estabelecimento estabelecimentoAtualizado = EstabelecimentoMapper.of(estabelecimentoDto, estabelecimento);
        estabelecimentoAtualizado.setId(id);
        return estabelecimentoRepository.save(estabelecimentoAtualizado) ;
    }

    public void deletar(Long id){
        if (!estabelecimentoRepository.existsById(id)){
            throw new EntidadeNaoExisteException("O estabelecimento procurado não existe.");
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.getReferenceById(id);
        estabelecimento.setIsAtivo(false);
        estabelecimentoRepository.save(estabelecimento);
    }


    public Long calcularRotaPessoa(CoordenadaDto coordenadaDto) {
        Pessoa pessoa = new Pessoa();

        return pessoa.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaBicicleta(CoordenadaDto coordenadaDto) {
        Bicicleta bicleta = new Bicicleta();

        return bicleta.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

    public Long calcularRotaCarro(CoordenadaDto coordenadaDto) {
        Carro carro = new Carro();

        return carro.calcularTempoDeslocamento(coordenadaDto.getX(), coordenadaDto.getY(), coordenadaDto.getToX(), coordenadaDto.getToY());
    }

}
