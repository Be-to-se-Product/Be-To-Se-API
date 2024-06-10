package com.be.two.c.apibetwoc.service.produto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.controller.tag.TagDTO;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.MetodoPagamentoAceitoService;
import com.be.two.c.apibetwoc.service.SecaoService;
import com.be.two.c.apibetwoc.service.arquivo.IStorage;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import com.be.two.c.apibetwoc.service.produto.mapper.ProdutoTagMapper;
import com.be.two.c.apibetwoc.service.produto.specification.ProdutoSpecification;
import com.be.two.c.apibetwoc.service.tag.mapper.TagMapper;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {


    private final ProdutoRepository produtoRepository;
    private final SecaoService secaoService;
    private final SecaoRepository secaoRepository;
    private final TagRepository tagRepository;
    private final ProdutoTagRepository produtoTagRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ImagemService imagemService;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final ImagemRepository imagemRepository;
    private final IStorage arquivoService;
    private final AvaliacaoRepository avaliacaoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemVendaRepository itemVendaRepository;

    public Produto buscarPorId(Long id) {
        Produto produto =  produtoRepository.findById(id).orElseThrow(EntidadeNaoExisteException::new);

       List<Imagem>  imagems =  produto.getImagens();
        produto.setImagens(imagems);
       return produto;
    }

    public ProdutoDetalhamentoDto buscarProdutoPorId(Long id) {
        Produto produto = buscarPorId(id);
        ProdutoDetalhamentoDto pd = ProdutoMapper.toProdutoDetalhamento(produto);
        List<MetodoPagamentoAceito> ma = metodoPagamentoAceitoService.findByEstabelecimentoId(pd.getSecao().getEstabelecimento().getId());
        List<Long> listaIds = ma.stream()
                .map(MetodoPagamentoAceito::getId).toList();
        pd.getSecao().getEstabelecimento().setIdMetodo(listaIds);

        return pd;
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAllByIsAtivoTrue();
        produtos.forEach(e -> e.getImagens().forEach(p -> System.out.println(p.getNomeReferencia())));
        int n = produtos.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                if (produtos.get(j).getNome().compareTo(produtos.get(j + 1).getNome()) > 0) {
                    Produto temp = produtos.get(j);
                    produtos.set(j, produtos.get(j + 1));
                    produtos.set(j + 1, temp);
                }
            }
        }

        return produtos;

    }



    @Transactional
    public void cadastrarImagens(List<MultipartFile> imagens, Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntidadeNaoExisteException("Produto não existe"));
        PilhaObj<ArquivoSaveDTO> imagensSalvas = new PilhaObj<>(imagens.size());
        imagemRepository.deleteByIdIn(produto.getImagens().stream().map(Imagem::getId).toList());
        List<Imagem> imagensSalvasLocal = imagens.stream().map(element -> imagemService.cadastrarImagensProduto(element, TipoArquivo.IMAGEM, produto, imagensSalvas)).toList();

        imagemRepository.saveAll(imagensSalvasLocal);
    }

    public Produto cadastrarProduto(CadastroProdutoDto cadastroProdutoDto) {
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao())
                .orElseThrow(() -> new EntidadeNaoExisteException("Seção não encontrada"));
        Produto produto = ProdutoMapper.toProduto(cadastroProdutoDto, secao);
        Produto produtoSalvo = produtoRepository.save(produto);
        List<Tag> tags = cadastroProdutoDto.getTags().stream().map(TagMapper::toTag).toList();
        List<Long> tagsId = tags.stream().map(Tag::getId).toList();
        List<Tag> tagBanco = tagRepository.findByIdIn(tagsId);
        List<ProdutoTag> produtoTags = tagBanco.stream().map(e -> new ProdutoTag(null, e, produtoSalvo)).toList();
        List<ProdutoTag> tagSalvos = produtoTagRepository.saveAll(produtoTags);
        produtoSalvo.setTags(tagSalvos);
        return produtoSalvo;
    }


    @Transactional
    public Produto atualizarProduto(Long id, CadastroProdutoDto cadastroProdutoDto) {
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao()).orElseThrow(() -> new EntidadeNaoExisteException("Seção não encontrada"));
        List<ProdutoTag> tagsBanco = produtoTagRepository.findByProdutoId(id);
        List<TagDTO> tagSalvar = cadastroProdutoDto.getTags();
        List<TagDTO> tagsDuplicadas = tagSalvar.stream()
                .filter(tagDto -> Collections.frequency(tagSalvar, tagDto) > 0)
                .distinct()
                .toList();


        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoExisteException("Não existe"));

        List<ProdutoTag> produtoTagsSalvar = tagsDuplicadas.stream()
                .map(tagDto -> ProdutoTagMapper.toProdutoTag(tagDto, produto))
                .toList();

        produtoTagRepository.deleteAllByProdutoId(id);
        produtoTagRepository.saveAll(produtoTagsSalvar);
        return produtoRepository.save(ProdutoMapper.toProduto(cadastroProdutoDto, secao, id));

    }


    public void inativarProduto(Long id) {
        Produto produto = buscarPorId(id);
        produto.setIsAtivo(false);
        produtoRepository.save(produto);
    }

    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = buscarPorId(id);
        List<Imagem> imagens = produto.getImagens();
        List<Avaliacao> avaliacaos = produto.getAvaliacoes();
        List<Carrinho> carrinhos = produto.getCarrinhos();
        List<ItemVenda> itemVendas = produto.getItemVendas();
        List<ProdutoTag> prodTags = produto.getTags();
        List<Long> idCarrinho = carrinhos.stream().filter(e -> Objects.equals(e.getProduto().getId(), id)).map(Carrinho::getId).toList();
        carrinhos.forEach((e) -> System.out.println(e.getProduto().getId()));
        imagens.forEach(e -> arquivoService.deletarArquivo(e.getNomeReferencia(), TipoArquivo.IMAGEM));
        List<Integer> idImagens = imagens.stream().map(Imagem::getId).toList();
        List<Long> idAvaliacao = avaliacaos.stream().map(Avaliacao::getId).toList();
        List<Long> idItemVenda = itemVendas.stream().filter(e -> Objects.equals(e.getProduto().getId(), id)).map(ItemVenda::getId).toList();
        List<Long> idTag = prodTags.stream().filter(e -> Objects.equals(e.getProduto().getId(), id)).map(ProdutoTag::getId).toList();
        produtoTagRepository.deleteByIdIn(idTag);
        carrinhoRepository.deleteByIdIn(idCarrinho);
        itemVendaRepository.deleteByIdIn(idItemVenda);
        avaliacaoRepository.deleteByIdIn(idAvaliacao);
        imagemRepository.deleteByIdIn(idImagens);
        produto.setIsAtivo(false);
        produtoRepository.save(produto);
    }

    public void statusProduto(boolean status, Long id) {
       buscarPorId(id);
        produtoRepository.statusProduto(status, id);
    }

    public void statusPromocaoProduto(Long id, boolean status) {
        buscarPorId(id);
        produtoRepository.statusPromocao(status, id);
    }

    public List<Produto> produtoPorEstabelecimento(Long id) {

        if (!estabelecimentoRepository.existsById(id)) {
            throw new EntidadeNaoExisteException("Entidade não existe");
        }

        return produtoRepository.findBySecaoEstabelecimentoIdAndIsAtivoTrue(id);
    }

    public Page<Produto> produtoPorEstabelecimento(Long id, Pageable pageable) {
        Specification<Produto> specification = Specification.where(ProdutoSpecification.isAtivoByEstabelecimento(id));
        if (!estabelecimentoRepository.existsById(id)) {
            throw new EntidadeNaoExisteException("Entidade não existe");
        }

        return produtoRepository.findAll(specification,pageable);
    }

    public List<Produto> barraDePesquisa(Long id, String pesquisa) {
        return produtoRepository.findBySecaoEstabelecimentoIdAndNomeContainsIgnoreCase(id, pesquisa);
    }

    public List<Produto> uploadCsv(MultipartFile file, Long secaoId) {

        List<Produto> produtos = new ArrayList<>();
        Secao secao = secaoService.listarPorId(secaoId);

        try {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();

            Reader reader = new InputStreamReader(file.getInputStream());

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            List<String[]> linhas = csvReader.readAll();

            if (linhas.isEmpty()) {
                throw new RuntimeException("Arquivo vazio");
            }

            for (String[] linha : linhas) {

                String valor = linha[2].replaceAll(",", ".")
                        .replaceAll("R\\$", "");
                String valorPromocao = linha[4].replaceAll(",", ".")
                        .replaceAll("R\\$", "");

                Produto produto = new Produto();
                produto.setNome(linha[0]);
                produto.setCodigoSku(linha[1]);
                produto.setPreco(Double.parseDouble(valor));
                produto.setDescricao(linha[3]);
                produto.setPrecoOferta(Double.parseDouble(valorPromocao));
                produto.setCodigoBarras(linha[5]);
                produto.setCategoria(linha[6]);
                produto.setIsAtivo(true);
                produto.setIsPromocaoAtiva(false);
                produto.setSecao(secao);
                produto.setImagens(new ArrayList<>());

                produtos.add(produto);
            }

            produtoRepository.saveAll(produtos);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar arquivo");
        } catch (CsvException e) {
            throw new RuntimeException("Falha ao ler arquivo");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Arquivo inválido");
        }

        return produtos;
    }

    public List<Produto> uploadTxt(MultipartFile file, Long secaoId) {
        List<Produto> produtos = new ArrayList<>();
        Secao secao = secaoService.listarPorId(secaoId);

        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            BufferedReader entrada = new BufferedReader(reader);

            String registro = entrada.readLine();

            while (registro != null) {
                if (registro.substring(0, 2).equals("02")) {
                    Produto produto = new Produto();

                    produto.setNome(registro.substring(2, 22));
                    produto.setCodigoSku(registro.substring(22, 32));
                    produto.setPreco(Double.valueOf(registro.substring(32, 38)));
                    produto.setDescricao(registro.substring(38, 68));
                    produto.setPrecoOferta(Double.valueOf(registro.substring(68, 74)));
                    produto.setCodigoBarras(registro.substring(74, 87));
                    produto.setCategoria(registro.substring(87, 107));
                    produto.setImagens(new ArrayList<>());
                    produto.setSecao(secao);
                    produto.setIsAtivo(true);

                    Produto produtoCriado = produtoRepository.save(produto);
                    produtos.add(produtoCriado);
                }

                registro = entrada.readLine();
            }

            entrada.close();
            return produtos;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar arquivo");
        }
    }

    public byte[] downloadCsv(Long idEstabelecimento) {
        List<Produto> produtos = produtoRepository.findBySecaoEstabelecimentoIdAndIsAtivoTrue(idEstabelecimento);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(outputStreamWriter)
                    .withSeparator(';')
                    .build();

            String[] cabecalho = {"Nome", "Codigo SKU", "Preço", "Descrição", "Preco Oferta", "Código de Barras", "Categoria", "Status", "Status Promoção", "Seção"};
            csvWriter.writeNext(cabecalho);

            for (Produto p : produtos) {
                String[] linha = {p.getNome(), p.getCodigoSku(), p.getPreco().toString(), p.getDescricao(), p.getPrecoOferta() != null ? p.getPrecoOferta().toString() : "0,00", p.getCodigoBarras(), p.getCategoria(), p.getIsAtivo() ? "Ativo" : "Inativo", p.getIsPromocaoAtiva() ? "Ativo" : "Inativo", p.getSecao().getDescricao()};

                csvWriter.writeNext(linha);
            }

            csvWriter.close();
            outputStreamWriter.close();

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produto> buscarProdutosParaVenda(List<Long> ids) {
        return produtoRepository.findByIdIn(ids);
    }

    public List<Produto> produtoEmPromocao() {
        return null;
    }
}
