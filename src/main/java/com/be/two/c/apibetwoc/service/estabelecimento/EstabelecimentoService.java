package com.be.two.c.apibetwoc.service.estabelecimento;

import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.AgendaMapper;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoAtualizarDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.AutenticacaoService;
import com.be.two.c.apibetwoc.service.EnderecoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.estabelecimento.specification.EstabelecimentoSpecification;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ComercianteRepository comercianteRepository;
    private final AgendaRepository agendaRepository;
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
        formatarImagem(estabelecimento);

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
        Optional<Comerciante> optionalComerciante = Optional.ofNullable(usuario.getComerciante());
        Comerciante comerciante = comercianteRepository.findById(optionalComerciante.orElseThrow(EntityNotFoundException::new).getId()).orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum comerciante com esse id"));
        Estabelecimento estabelecimento = EstabelecimentoMapper.toEstabelecimento(estabelecimentoCadastroDTO, comerciante);
        Endereco endereco = enderecoService.cadastrar(estabelecimentoCadastroDTO.getEndereco().getCep(), estabelecimentoCadastroDTO.getEndereco().getNumero());
        estabelecimento.setEndereco(endereco);
        Estabelecimento estabelecimentoCriado = estabelecimentoRepository.save(estabelecimento);
        List<Agenda> agenda = agendaRepository.saveAll(AgendaMapper.of(estabelecimentoCadastroDTO.getHorarios(), estabelecimentoCriado));
        estabelecimentoCriado.setAgenda(agenda);
        return estabelecimentoCriado;
    }

    @Transactional
    public Estabelecimento atualizarEstabelecimento(EstabelecimentoAtualizarDTO estabelecimentoDto, Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(() -> new EntidadeNaoExisteException("Entidade não encontrada"));
        agendaRepository.deleteByEstabelecimentoId(id);
        List<MetodoPagamentoAceito> metodoPagamentosBanco= metodoPagamentoAceitoRepository.findByEstabelecimentoId(id);
        List<MetodoPagamento> metodoPagamentoFront = metodoPagamentoRepository.findByIdIn(estabelecimentoDto.getMetodoPagamento());
        List<MetodoPagamentoAceito> metodoPagamentoRemover = new ArrayList<>();

        for (MetodoPagamentoAceito metodoPagamentoBanco  : metodoPagamentosBanco ) {
            if(metodoPagamentoFront.stream().noneMatch(e->metodoPagamentoBanco.getMetodoPagamento().getId()==e.getId())){
                metodoPagamentoBanco.setIsAtivo(false);
                metodoPagamentoRemover.add(metodoPagamentoBanco);
            }
        }

        metodoPagamentoAceitoRepository.saveAll(metodoPagamentoRemover);

        List<MetodoPagamentoAceito> metodoPagamentoSalvar = new ArrayList<>();
        for (MetodoPagamento metodoPagamento: metodoPagamentoFront ) {
            if(metodoPagamentosBanco.stream().noneMatch(e-> e.getId().equals(metodoPagamento.getId()))){
                MetodoPagamentoAceito metodoPagamentoAceito = new MetodoPagamentoAceito();
                metodoPagamentoAceito.setEstabelecimento(estabelecimento);
                metodoPagamentoAceito.setMetodoPagamento(metodoPagamento);
                metodoPagamentoAceito.setIsAtivo(true);
            }
        }

        Set<MetodoPagamentoAceito> metodoPagamentoAceitoHashSet = new HashSet<>(metodoPagamentoSalvar);

        for (MetodoPagamentoAceito metodoPagamentoAceito: metodoPagamentosBanco) {
            metodoPagamentoAceitoHashSet.add(metodoPagamentoAceito);
        }
        List<MetodoPagamentoAceito> metodos = metodoPagamentoAceitoHashSet.stream().toList();
        EstabelecimentoMapper.toEstabelecimento(estabelecimentoDto,estabelecimento);
        Estabelecimento estabelecimentoSalvo =  estabelecimentoRepository.save(estabelecimento);
        metodoPagamentoAceitoRepository.saveAll(metodos);
        Endereco endereco = enderecoRepository.findByEstabelecimentoId(id);
        endereco.setNumero(estabelecimentoDto.getEndereco().getNumero());
        endereco.setRua(estabelecimentoDto.getEndereco().getLogradouro());
        endereco.setBairro(estabelecimentoDto.getEndereco().getBairro());
        endereco.setCep(estabelecimentoDto.getEndereco().getCep());
        List<Secao> secaoSalvar = estabelecimentoDto.getSecao().stream().map(e -> EstabelecimentoMapper.toSecao(e, estabelecimentoSalvo)).toList();
        secaoRepository.saveAll(secaoSalvar);

        List<Agenda> agendaNova = agendaRepository.saveAll(estabelecimentoDto.getAgenda().stream().map(AgendaMapper::toAgenda).toList());
        estabelecimentoSalvo.setAgenda(agendaNova);
        System.out.println(estabelecimento.getAgenda().size());
        enderecoRepository.save(endereco);
        estabelecimentoRepository.save(estabelecimentoSalvo);

        return estabelecimentoSalvo;
    }

    public void deletar(Long id) {
        if (!estabelecimentoRepository.existsById(id)) {
            throw new EntidadeNaoExisteException("O estabelecimento procurado não existe.");
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.getReferenceById(id);
        estabelecimento.setIsAtivo(false);
        estabelecimentoRepository.save(estabelecimento);
    }

    private void formatarImagem(Estabelecimento estabelecimento) {

        estabelecimento.getImagens().stream().forEach(element -> element.setNomeReferencia(imagemService.formatterImagensURI(element).getNomeReferencia()));

    }

    private Long retornarIdUsuario() {
        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();
        return idUsuario;
    }

    public List<Estabelecimento> listarPorComerciante(String nome) {

        Long usuarioId = retornarIdUsuario();
        Specification<Estabelecimento> spec = Specification.where(EstabelecimentoSpecification.id(usuarioId).and(EstabelecimentoSpecification.name(nome)));
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll(spec);
        for (Estabelecimento estabelecimento : estabelecimentos) {
            if (estabelecimento.getImagens() != null) {
                estabelecimento.getImagens().stream().forEach(element -> element.setNomeReferencia(imagemService.formatterImagensURI(element).getNomeReferencia()));
            }
            if (estabelecimento.getMetodoPagamentoAceito() != null) {
                estabelecimento.setMetodoPagamentoAceito(estabelecimento.getMetodoPagamentoAceito().stream().filter(e -> e.getIsAtivo()).toList());
            }
        }
        return estabelecimentos;
    }

    public void salvarImagem(MultipartFile imagem, Long id) {
        PilhaObj<ArquivoSaveDTO> arquivos = new PilhaObj<>(1);
        Estabelecimento estabelecimento = listarPorId(id);
        Imagem imagemSalva = imagemService.cadastrarImagensEstabelecimento(imagem, TipoArquivo.IMAGEM, estabelecimento, arquivos);
        imagemRepository.save(imagemSalva);
    }
}
