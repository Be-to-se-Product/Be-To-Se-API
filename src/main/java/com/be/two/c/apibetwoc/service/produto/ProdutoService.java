package com.be.two.c.apibetwoc.service.produto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;

import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.MetodoPagamentoAceitoService;
import com.be.two.c.apibetwoc.service.SecaoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import com.be.two.c.apibetwoc.util.PilhaObj;
import com.be.two.c.apibetwoc.util.TipoArquivo;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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


    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Produto não encontrado")
        );
    }
    public ProdutoDetalhamentoDto buscarProdutoPorId(Long id){
        Produto produto = buscarPorId(id);
        ProdutoDetalhamentoDto pd = ProdutoMapper.toProdutoDetalhamento(produto);
        List<MetodoPagamentoAceito> ma = metodoPagamentoAceitoService.findByEstabelecimentoId(pd.getSecao().getEstabelecimento().getId());
        List<Long> listaIds = ma.stream()
                .map(MetodoPagamentoAceito::getId).toList();
        pd.getSecao().getEstabelecimento().setIdMetodo(listaIds);
        return pd ;
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
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

    public Produto cadastrarProduto(CadastroProdutoDto cadastroProdutoDto, List<MultipartFile> imagens ) {
        PilhaObj<ArquivoSaveDTO> imagensSalvas = new PilhaObj<>(imagens.size());
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao())
                .orElseThrow(() -> new EntidadeNaoExisteException("Seção não encontrada"));
        Produto produto = ProdutoMapper.toProduto(cadastroProdutoDto,secao);
        Produto produtoSalvo = produtoRepository.save(produto);

        if (cadastroProdutoDto.getTag() != null){
            for (Long idTag : cadastroProdutoDto.getTag()) {
                Tag tag = tagRepository.findById(idTag).orElse(null);

                produtoTagRepository.save(new ProdutoTag(null, tag, produtoSalvo));
            }
        }

        List<Imagem> imagensSalvasLocal = imagens.stream().map(element-> imagemService.cadastrarImagensProduto(element,TipoArquivo.IMAGEM,produtoSalvo,imagensSalvas)).toList();
        List<Imagem> imagensCadastradas = imagemRepository.saveAll(imagensSalvasLocal);
        imagensCadastradas.stream().forEach(element->element.setNomeReferencia(imagemService.formatterImagensURI(element).getNomeReferencia()));
        produtoSalvo.setImagens(imagensCadastradas);
        return produtoSalvo;
    }




    public Produto atualizarProduto(Long id, CadastroProdutoDto cadastroProdutoDto) {

        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao()).get();

        buscarPorId(id);

        Produto produto = ProdutoMapper.toProduto(cadastroProdutoDto,secao,id);


        Produto produtoSalvo = produtoRepository.save(produto);
        List<ProdutoTag> tagsProduto = produtoTagRepository.buscarPorProduto(produto.getId());
        List<Long> tagEdicao = cadastroProdutoDto.getTag();

        for (Long tagAtualizada : tagEdicao) {
            boolean tagJaAssociada = false;
            Tag tag = tagRepository.findById(tagAtualizada).orElse(null);

            for (ProdutoTag produtoTag : tagsProduto) {
                if (produtoTag.getTag().getId().equals(tagAtualizada)) {
                    tagJaAssociada = true;
                    break;
                }
            }

            if (!tagJaAssociada) {
                ProdutoTag novaProdutoTag = new ProdutoTag(null, tag, produtoSalvo);
                produtoTagRepository.save(novaProdutoTag);
            }
        }

        for (ProdutoTag produtoTag : tagsProduto) {
            boolean encontrada = false;
            for (Long tagAtualizada : tagEdicao) {
                if (produtoTag.getTag().getId().equals(tagAtualizada)) {
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                produtoTagRepository.delete(produtoTag);
            }
        }

        return produtoSalvo;
    }

    public void inativarProduto(Long id) {
        Produto produto = buscarPorId(id);
        produto.setIsAtivo(false);
        produtoRepository.save(produto);
    }

    public void statusProduto(boolean status, Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.statusProduto(status, id);
    }

    public void statusPromocaoProduto(Long id, boolean status) {
        Produto produto = buscarPorId(id);
        produtoRepository.statusPromocao(status, id);
    }

    public List<Produto> produtoPorEstabelecimento(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoExisteException("Estabelecimento não encontrado")
        );

        List<Produto> produtos = produtoRepository.findBySecaoEstabelecimentoId(id);

        return produtos;
    }

    public List<Produto> barraDePesquisa(Long id, String pesquisa) {
        return produtoRepository.findBySecaoEstabelecimentoIdAndNomeContainsIgnoreCase(id, pesquisa);
    }

    public List<Produto> produtoEmPromocao() {
        return produtoRepository.findByIsPromocaoAtivaTrue();
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

    public byte[] downloadCsv(Long idEstabelecimento) {
        List<Produto> produtos = produtoRepository.findBySecaoEstabelecimentoId(idEstabelecimento);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(outputStreamWriter)
                    .withSeparator(';')
                    .build();

            String[] cabecalho = {"Nome", "Codigo SKU", "Preço", "Descrição", "Preco Oferta", "Código de Barras", "Categoria", "Status", "Status Promoção", "Seção"};
            csvWriter.writeNext(cabecalho);

            for (Produto p : produtos) {
                String[] linha = {p.getNome(), p.getCodigoSku(), p.getPreco().toString(), p.getDescricao(), p.getPrecoOferta().toString(), p.getCodigoBarras(), p.getCategoria(), p.getIsAtivo() ? "Ativo" : "Inativo", p.getIsPromocaoAtiva() ? "Ativo" : "Inativo", p.getSecao().getDescricao()};

                csvWriter.writeNext(linha);
            }

            csvWriter.close();
            outputStreamWriter.close();
            byte[] csvBytes = byteArrayOutputStream.toByteArray();

            return csvBytes;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
