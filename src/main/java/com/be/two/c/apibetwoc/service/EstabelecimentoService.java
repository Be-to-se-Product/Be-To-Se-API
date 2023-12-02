package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.AgendaMapper;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoAtualizarDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ComercianteRepository comercianteRepository;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final AgendaRepository agendaRepository;
    private final SecaoRepository secaoRepository;
    private final EnderecoRepository enderecoRepository;
    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository usuarioRepository;

    public Estabelecimento listarPorId(Long id){
        return estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));
    }

    public List<Estabelecimento> listarTodos(){
        return estabelecimentoRepository.findAll();
    }

    public List<Estabelecimento> listarPorSegmento(String segmento){
        return estabelecimentoRepository.findBySegmento(segmento);
    }

    @Transactional
    public Estabelecimento cadastroEstabelecimento(EstabelecimentoCadastroDTO estabelecimentoCadastroDTO){
        Usuario usuario = usuarioRepository.findById(autenticacaoService.loadUsuarioDetails().getId()).orElseThrow(EntityNotFoundException::new);
        Optional<Comerciante> optionalComerciante = Optional.ofNullable(usuario.getComerciante());

        Comerciante comerciante = comercianteRepository
                .findById(optionalComerciante.orElseThrow(EntityNotFoundException::new).getId())
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum comerciante com esse id"));

        Estabelecimento estabelecimento = EstabelecimentoMapper.toEstabelecimento(estabelecimentoCadastroDTO, comerciante);
        Endereco enderecoEntity = EstabelecimentoMapper.of(estabelecimentoCadastroDTO.getEnderecoDto());


        Endereco endereco =  enderecoRepository.save(enderecoEntity);

        estabelecimento.setEndereco(endereco);
        Estabelecimento estabelecimentoCriado = estabelecimentoRepository.save(estabelecimento);
        List<MetodoPagamentoAceito> metodoPagamentoAceitos = metodoPagamentoAceitoService.cadastrarMetodosPagamentos(estabelecimentoCriado, estabelecimentoCadastroDTO.getIdMetodoPagamento());
        List<Agenda> agenda =agendaRepository.saveAll(AgendaMapper.of(estabelecimentoCadastroDTO.getAgenda(), estabelecimentoCriado));
        List<Secao> secao =  secaoRepository.saveAll(SecaoMapper.of(estabelecimentoCadastroDTO.getSecao(), estabelecimentoCriado));
        estabelecimentoCriado.setAgenda(agenda);
        estabelecimentoCriado.setSecao(secao);
        estabelecimentoCriado.setMetodoPagamentoAceito(metodoPagamentoAceitos);
        return estabelecimentoCriado;
    }

    public Estabelecimento atualizarEstabelecimento(EstabelecimentoAtualizarDTO estabelecimentoDto, Long id){
        Estabelecimento estabelecimento = listarPorId(id);
        Estabelecimento estabelecimentoAtualizado = EstabelecimentoMapper.toEstabelecimento(estabelecimentoDto, estabelecimento);
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



    private Long retornarIdUsuario () {
        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();
        return idUsuario;
    }
    public List<Estabelecimento> listarPorComerciante() {
        Long usuarioId = retornarIdUsuario();
        return estabelecimentoRepository.findByComercianteUsuarioId(usuarioId);
    }
}
