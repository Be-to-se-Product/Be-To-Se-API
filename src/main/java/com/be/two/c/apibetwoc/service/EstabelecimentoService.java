package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.AgendaMapper;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoAtualizarDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.dto.EstabelecimentoCadastroDTO;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.controller.produto.dto.mapa.AgendaResponseDTO;
import com.be.two.c.apibetwoc.controller.secao.mapper.SecaoMapper;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImagemService imagemService;
    private final ImagemRepository imagemRepository;
    private final EnderecoService enderecoService;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;


    public Estabelecimento listarPorId(Long id){

        Estabelecimento estabelecimento = estabelecimentoRepository
                .findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe nenhum estabelecimento com esse id"));
        formatarImagem(estabelecimento);
        return estabelecimento;
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
        Endereco endereco = enderecoService.cadastrar(estabelecimentoCadastroDTO.getEndereco().getCep(),estabelecimentoCadastroDTO.getEndereco().getNumero());
        estabelecimento.setEndereco(endereco);

        Estabelecimento estabelecimentoCriado = estabelecimentoRepository.save(estabelecimento);
        List<MetodoPagamentoAceito> metodoPagamentoAceitos = metodoPagamentoAceitoService.cadastrarMetodosPagamentos(estabelecimentoCriado, estabelecimentoCadastroDTO.getMetodoPagamento());
        List<Agenda> agenda =agendaRepository.saveAll(AgendaMapper.of(estabelecimentoCadastroDTO.getAgenda(), estabelecimentoCriado));
        List<Secao> secao =  secaoRepository.saveAll(SecaoMapper.of(estabelecimentoCadastroDTO.getSecao(), estabelecimentoCriado));
        estabelecimentoCriado.setAgenda(agenda);
        estabelecimentoCriado.setSecao(secao);
        estabelecimentoCriado.setMetodoPagamentoAceito(metodoPagamentoAceitos);
        return estabelecimentoCriado;
    }

    public Estabelecimento atualizarEstabelecimento(EstabelecimentoAtualizarDTO estabelecimentoDto, Long id){
        Estabelecimento estabelecimento = listarPorId(id);
        agendaRepository.deleteByEstabelecimentoId(id);
        metodoPagamentoAceitoRepository.deleteByEstabelecimentoId(id);
        enderecoRepository.deleteByEstabelecimentoId(id);

        List<Secao> secaoSalvar = estabelecimentoDto.getSecao().stream().map(e->EstabelecimentoMapper.toSecao(e,estabelecimento)).toList();
        List<Secao> secaos = secaoRepository.saveAll(secaoSalvar);
        List<Agenda> agenda= agendaRepository.saveAll(estabelecimentoDto.getAgenda().stream().map(AgendaMapper::toAgenda).toList());
        List<MetodoPagamentoAceito> metodoPagamentoAceito =  metodoPagamentoAceitoService.cadastrarMetodosPagamentos(estabelecimento,estabelecimentoDto.getMetodoPagamento());
        Endereco endereco = enderecoService.cadastrar(estabelecimento.getEndereco().getCep(),estabelecimento.getEndereco().getNumero());
        Estabelecimento estabelecimentoSave = EstabelecimentoMapper.toEstabelecimento(estabelecimentoDto,estabelecimento,agenda,endereco,metodoPagamentoAceito);
        return estabelecimentoRepository.save(estabelecimentoSave);

    }

    public void deletar(Long id){
        if (!estabelecimentoRepository.existsById(id)){
            throw new EntidadeNaoExisteException("O estabelecimento procurado não existe.");
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.getReferenceById(id);
        estabelecimento.setIsAtivo(false);
        estabelecimentoRepository.save(estabelecimento);
    }

private void  formatarImagem(Estabelecimento estabelecimento){

        estabelecimento.getImagens().stream().forEach(element -> element.setNomeReferencia(imagemService.formatterImagensURI(element).getNomeReferencia()));

}

    private Long retornarIdUsuario () {
        Long idUsuario = autenticacaoService.loadUsuarioDetails().getId();
        return idUsuario;
    }
    public List<Estabelecimento> listarPorComerciante() {
        Long usuarioId = retornarIdUsuario();
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findByComercianteUsuarioId(usuarioId);
        for (Estabelecimento estabelecimento:
             estabelecimentos) {
            estabelecimento.getImagens().stream().forEach(element -> element.setNomeReferencia(imagemService.formatterImagensURI(element).getNomeReferencia()));
        }

        return estabelecimentos;
    }

    public void salvarImagem(MultipartFile imagem, Long id) {
        PilhaObj<ArquivoSaveDTO> arquivos = new PilhaObj<>(1);
        Estabelecimento estabelecimento =  listarPorId(id);
        Imagem imagemSalva=  imagemService.cadastrarImagensEstabelecimento(imagem, TipoArquivo.IMAGEM,estabelecimento,arquivos);
        imagemRepository.save(imagemSalva);
    }
}
