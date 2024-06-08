package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.AgendaMapper;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoAtualizarDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.arquivo.IStorage;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ComercianteRepository comercianteRepository;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final AgendaRepository agendaRepository;
    private final AgendaService agendaService;
    private final SecaoRepository secaoRepository;
    private final EnderecoRepository enderecoRepository;
    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository usuarioRepository;
    private final ImagemService imagemService;
    private final ImagemRepository imagemRepository;
    private final EnderecoService enderecoService;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;

    public Estabelecimento listarPorId(Long id) {

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));
        estabelecimento.setMetodoPagamentoAceito(estabelecimento.getMetodoPagamentoAceito().stream().filter(MetodoPagamentoAceito::getIsAtivo).toList());
        return estabelecimento;
    }

    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }

    public List<Estabelecimento> listarPorSegmento(String segmento) {
        return estabelecimentoRepository.findBySegmento(segmento);
    }

    @Transactional
    public Estabelecimento cadastroEstabelecimento(EstabelecimentoCadastroDTO estabelecimentoCadastroDTO) {
        Usuario usuario = usuarioRepository.findById(autenticacaoService.loadUsuarioDetails().getId()).orElseThrow(EntityNotFoundException::new);
        Comerciante comercianteFind = Optional.ofNullable(usuario.getComerciante()).orElseThrow(EntityNotFoundException::new);
        Comerciante comerciante = comercianteRepository.findById(comercianteFind.getId()).orElseThrow(EntityNotFoundException::new);
        Estabelecimento estabelecimento = EstabelecimentoMapper.toEstabelecimento(estabelecimentoCadastroDTO, comerciante);
        Endereco endereco = enderecoService.cadastrar(estabelecimentoCadastroDTO.getEndereco().getCep(), estabelecimentoCadastroDTO.getEndereco().getNumero());
        estabelecimento.setEndereco(endereco);
         metodoPagamentoAceitoService
                .cadastrarMetodosPagamentos(estabelecimento,
                        estabelecimentoCadastroDTO.getMetodoPagamento());
        Estabelecimento estabelecimentoCriado = estabelecimentoRepository.save(estabelecimento);
        if (!estabelecimentoCadastroDTO.getAgenda().isEmpty()) {
            List<Agenda> agenda = agendaService.cadastrarAgenda(estabelecimentoCadastroDTO.getAgenda(), estabelecimentoCriado);
            estabelecimentoCriado.setAgenda(agenda);
        }


        return estabelecimentoCriado;
    }

    @Transactional
    public Estabelecimento atualizarEstabelecimento(EstabelecimentoAtualizarDTO estabelecimentoDto, Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Entidade não encontrada"));

        agendaRepository.deleteByEstabelecimentoId(id);

        List<MetodoPagamentoAceito> metodoPagamentosBanco = metodoPagamentoAceitoRepository.findByEstabelecimentoIdAndIsAtivoTrue(id);
        List<MetodoPagamento> metodoPagamentoFront = metodoPagamentoRepository.findByIdIn(estabelecimentoDto.getMetodoPagamento());
        List<MetodoPagamentoAceito> metodoPagamentoRemover = new ArrayList<>();

        for (MetodoPagamentoAceito metodoPagamentoBanco : metodoPagamentosBanco) {
            if (metodoPagamentoFront.stream().noneMatch(e -> metodoPagamentoBanco.getMetodoPagamento().getId().equals(e.getId()))) {
                metodoPagamentoBanco.setIsAtivo(false);
                metodoPagamentoRemover.add(metodoPagamentoBanco);
            }
        }

        metodoPagamentoAceitoRepository.saveAll(metodoPagamentoRemover);

        List<MetodoPagamentoAceito> metodoPagamentoSalvar = new ArrayList<>();
        for (MetodoPagamento metodoPagamento : metodoPagamentoFront) {
            if (metodoPagamentosBanco.stream().noneMatch(e -> e.getMetodoPagamento().getId().equals(metodoPagamento.getId()))) {
                MetodoPagamentoAceito metodoPagamentoAceito = new MetodoPagamentoAceito();
                metodoPagamentoAceito.setEstabelecimento(estabelecimento);
                metodoPagamentoAceito.setMetodoPagamento(metodoPagamento);
                metodoPagamentoAceito.setIsAtivo(true);
                metodoPagamentoSalvar.add(metodoPagamentoAceito);
            }
        }


        metodoPagamentoAceitoRepository.saveAll(metodoPagamentoSalvar);

        EstabelecimentoMapper.toEstabelecimento(estabelecimentoDto, estabelecimento);

        Endereco endereco = enderecoRepository.findByEstabelecimentoId(id);
        endereco.setNumero(estabelecimentoDto.getEndereco().getNumero());
        endereco.setRua(estabelecimentoDto.getEndereco().getLogradouro());
        endereco.setBairro(estabelecimentoDto.getEndereco().getBairro());
        endereco.setCep(estabelecimentoDto.getEndereco().getCep());
        enderecoRepository.save(endereco);

        List<Secao> secaoSalvar = new ArrayList<>(estabelecimentoDto.getSecao().stream()
                .map(e -> EstabelecimentoMapper.toSecao(e, estabelecimento))
                .toList());


       List<Secao> secao = secaoRepository.saveAll(secaoSalvar);

        List<Agenda> agendaNova = agendaRepository.saveAll(estabelecimentoDto.getAgenda().stream()
                .map(AgendaMapper::toAgenda)
                .toList());

        estabelecimento.setAgenda(agendaNova);

        estabelecimento.setSecao(secao);
        return estabelecimentoRepository.save(estabelecimento);
    }


    public void deletar(Long id) {
        if (!estabelecimentoRepository.existsById(id)) {
            throw new EntidadeNaoExisteException("O estabelecimento procurado não existe.");
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.getReferenceById(id);
        estabelecimento.setIsAtivo(false);
        estabelecimentoRepository.save(estabelecimento);
    }


    private Long retornarIdUsuario() {
        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();
        return idUsuario;
    }

    public List<Estabelecimento> listarPorComerciante() {
        Long usuarioId = retornarIdUsuario();

        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findByComercianteUsuarioIdAndIsAtivoTrue(usuarioId);
        for (Estabelecimento estabelecimento : estabelecimentos) {

            if (estabelecimento.getMetodoPagamentoAceito() != null) {
                estabelecimento.setMetodoPagamentoAceito(estabelecimento.getMetodoPagamentoAceito().stream().filter(MetodoPagamentoAceito::getIsAtivo).toList());
            }
        }
        return estabelecimentos;
    }

    @Transactional
    public void salvarImagem(MultipartFile imagem, Long id) {
        PilhaObj<ArquivoSaveDTO> arquivos = new PilhaObj<>(1);
        Estabelecimento estabelecimento = listarPorId(id);
        imagemRepository.deleteByIdIn(estabelecimento.getImagens().stream().map(Imagem::getId).toList());
        Imagem imagemSalva = imagemService.cadastrarImagensEstabelecimento(imagem, TipoArquivo.IMAGEM, estabelecimento, arquivos);
        imagemRepository.save(imagemSalva);
    }
}
