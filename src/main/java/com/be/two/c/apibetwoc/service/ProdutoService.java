package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.Secao;
import com.be.two.c.apibetwoc.repository.ProdutoRepository;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final NewsletterService newsletterService;

    private final ProdutoRepository produtoRepository;
    private final SecaoService secaoService;

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto listarProdutoPorId(Long id){
        return produtoRepository.findById(id).get();
    }

    public Produto cadastrarProduto(CadastroProdutoDto produto){
        Produto produtoCadastrado=produtoRepository.save(new Produto(null, produto.getNome(), produto.getDescricao(), produto.getPrecoCompra(), produto.getPrecoOferta(), produto.getCategoria(), produto.getCodigoSku(), true, false, 0 ,null,null));
        newsletterService.publicarNewsletter(produtoCadastrado);
        return produtoCadastrado;
    }

    public Produto atualizarProduto(Long id, CadastroProdutoDto produto){
        Produto produtoOptional = produtoRepository.findById(id).get();

        return produtoRepository.save(new Produto(produtoOptional.getId(), produto.getNome(), produto.getDescricao(), produto.getPrecoCompra(), produto.getPrecoOferta(), produto.getCategoria(), produto.getCodigoSku(), false, false, 0 ,null,null));
    }

    public void deletarProduto(Long id){
        Produto produtoOptional = produtoRepository.findById(id).get();

        produtoRepository.deleteById(id);
    }

    public List<Produto> listarProdutosPorEstabelecimento(Long idEstabelecimento){
        return produtoRepository.findBySecaoEstabelecimentoId(idEstabelecimento);
    }

    public List<Produto> uploadCsv(MultipartFile file, String secaoSelecionada){
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
                String valorPromocao = linha[3].replaceAll(",", ".")
                        .replaceAll("R\\$", "");


                Produto produto = new Produto(null, linha[0], linha[1], Double.parseDouble(valor), Double.parseDouble(valorPromocao), linha[4], linha[5], true , false, Integer.valueOf(linha[6]), secao,null);

                produtos.add(produto);
            }

            produtoRepository.saveAll(produtos);

        } catch (IOException e){
            throw new RuntimeException("Falha ao processar arquivo");
        } catch (CsvException e){
            throw new RuntimeException("Falha ao ler arquivo");
        }

        return produtos;
    }

    public byte[] downloadCsv(Long idEstabelecimento){
        List<Produto> produtos = produtoRepository.findBySecaoEstabelecimentoId(idEstabelecimento);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(outputStreamWriter)
                    .withSeparator(';')
                    .build();

            String[] cabecalho = {"Nome", "Codigo SKU", "Preço", "Descrição", "Preco Oferta", "Categoria", "Seção", "Status", "Status Promoção", "Qtd Vendas"};
            csvWriter.writeNext(cabecalho);

            for (Produto p : produtos){
                String[] linha = {p.getNome(), p.getCodigoSku(), p.getPreco().toString(), p.getDescricao(), p.getPrecoOferta().toString(), p.getCategoria(), p.getSecao().getDescricao(), p.isAtivo() ? "Ativo" : "Inativo", p.isPromocaoAtiva() ? "Ativo" : "Inativo", String.valueOf(p.getQtdVendas())};

                csvWriter.writeNext(linha);
            }

            csvWriter.close();
            outputStreamWriter.close();
            byte[] csvBytes = byteArrayOutputStream.toByteArray();

            return csvBytes;

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
