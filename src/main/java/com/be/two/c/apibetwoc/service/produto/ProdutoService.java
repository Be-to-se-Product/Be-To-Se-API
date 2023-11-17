package com.be.two.c.apibetwoc.service.produto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.dto.TagDTO;
import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.*;
import com.be.two.c.apibetwoc.service.ImagemService;
import com.be.two.c.apibetwoc.service.NewsletterService;
import com.be.two.c.apibetwoc.service.SecaoService;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final NewsletterService newsletterService;

    private final ProdutoRepository produtoRepository;
    private final SecaoService secaoService;
    private final SecaoRepository secaoRepository;
    private final TagRepository tagRepository;
    private final ProdutoTagRepository produtoTagRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ImagemService imagemService;

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Produto não encontrado")
        );
    }

    public List<ProdutoDetalhamentoDto> listarProdutos() {
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
        return produtos.stream().map(produto -> {
            ProdutoDetalhamentoDto dto = new ProdutoDetalhamentoDto(produto);
            String imagemBase64  = produto.getImagens().stream()
                    .map(imagem -> imagemService.converterParaBase64(imagem.getNomeImagem()))
                    .findFirst().orElse(null);
            dto.setImagem(imagemBase64);
            return dto;
        }).collect(Collectors.toList());
    }

    public Produto cadastrarProduto(CadastroProdutoDto cadastroProdutoDto) {
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao())
                .orElseThrow(() -> new EntidadeNaoExisteException("Seção não encontrada"));
        Produto produto = ProdutoMapper.of(cadastroProdutoDto);
        produto.setSecao(secao);
        produto.setIsPromocaoAtiva(false);

        Produto produtoSalvo = produtoRepository.save(produto);

        if (cadastroProdutoDto.getTag() != null) {
            for (TagDTO tags : cadastroProdutoDto.getTag()) {
                Tag tag = tagRepository.findById(tags.getId()).orElse(null);

                if (tag == null) {
                    tag = new Tag();
                    tag.setDescricao(tags.getDescricao());
                    tag = tagRepository.save(tag);
                }

                produtoTagRepository.save(new ProdutoTag(null, tag, produtoSalvo));
            }
        }
        if (cadastroProdutoDto.getImagem1() != null) {
            imagemService.salvarImagem(cadastroProdutoDto.getImagem1(), "imagem1", produtoSalvo);
        }
        if (cadastroProdutoDto.getImagem2() != null) {
            imagemService.salvarImagem(cadastroProdutoDto.getImagem2(), "imagem2", produtoSalvo);
        }
        if (cadastroProdutoDto.getImagem3() != null) {
            imagemService.salvarImagem(cadastroProdutoDto.getImagem3(), "imagem3", produtoSalvo);
        }
        if (cadastroProdutoDto.getImagem4() != null) {
            imagemService.salvarImagem(cadastroProdutoDto.getImagem4(), "imagem4", produtoSalvo);
        }
        if (cadastroProdutoDto.getImagem5() != null) {
            imagemService.salvarImagem(cadastroProdutoDto.getImagem5(), "imagem5", produtoSalvo);
        }
        newsletterService.publicarNewsletter(produtoSalvo);
        return produtoSalvo;
    }

    public Produto atualizarProduto(Long id, CadastroProdutoDto cadastroProdutoDto) {
        Produto produto = buscarPorId(id);
        Secao secao = secaoRepository.findById(cadastroProdutoDto.getSecao()).get();
        produto = ProdutoMapper.of(cadastroProdutoDto);
        produto.setSecao(secao);
        produto.setId(produto.getId());

        Produto produtoSalvo = produtoRepository.save(produto);
        List<ProdutoTag> tagsProduto = produtoTagRepository.buscarPorProduto(produto.getId());
        List<TagDTO> tagEdicao = cadastroProdutoDto.getTag();

        for (TagDTO tags : tagEdicao) {
            boolean tagJaAssociada = false;
            assert tags.getId() != null;
            Tag tag = tagRepository.findById(tags.getId()).orElse(null);

            if (tag != null) {
                for (ProdutoTag produtoTag : tagsProduto) {
                    if (produtoTag.getTag().getId().equals(tags.getId())) {
                        tagJaAssociada = true;
                        break;
                    }
                }
            } else {
                tag = new Tag();
                tag.setDescricao(tags.getDescricao());
                tag = tagRepository.save(tag);
            }

            if (!tagJaAssociada) {
                ProdutoTag novaProdutoTag = new ProdutoTag(null, tag, produtoSalvo);
                produtoTagRepository.save(novaProdutoTag);
            }
        }

        for (ProdutoTag produtoTag : tagsProduto) {
            boolean encontrada = false;
            for (TagDTO tags : tagEdicao) {
                if (produtoTag.getTag().getId().equals(tags.getId())) {
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

    public void deletarProduto(Long id) {
        Produto produto = buscarPorId(id);
        List<ProdutoTag> tags = produtoTagRepository.buscarPorProduto(produto.getId());
        List<Long> produtoTagIds = tags.stream().map(ProdutoTag::getId).collect(Collectors.toList());
        for (Long tagDeletar : produtoTagIds) {
            produtoTagRepository.deleteById(tagDeletar);
        }
        produtoRepository.deleteById(id);
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
        return produtoRepository.buscaProdutosPorLoja(id);
    }

    public List<Produto> barraDePesquisa(String pesquisa) {
        return produtoRepository.buscarProdutoPorNomeOuTag(pesquisa);
    }

    public List<Produto> produtoEmPromocao() {
        return produtoRepository.findByIsPromocaoAtivaTrue();
    }

    public List<Produto> uploadCsv(MultipartFile file, String secaoSelecionada) {
        List<Produto> produtos = new ArrayList<>();
        Secao secao = secaoService.listarSecaoPorDescricao(secaoSelecionada);

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

            for (String[] linha : linhas) {
                String valor = linha[2].replaceAll(",", ".")
                        .replaceAll("R\\$", "");
                String valorPromocao = linha[4].replaceAll(",", ".")
                        .replaceAll("R\\$", "");


                Produto produto = new Produto();
//linha[0], linha[1], Double.parseDouble(valor), linha[3], Double.parseDouble(valorPromocao), linha[5], linha[6], true, false, secao
                produtos.add(produto);
            }

            produtoRepository.saveAll(produtos);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar arquivo");
        } catch (CsvException e) {
            throw new RuntimeException("Falha ao ler arquivo");
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
