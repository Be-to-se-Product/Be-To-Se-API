package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.produto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.dto.produto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.ImagemService;
import com.be.two.c.apibetwoc.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ImagemService imagemService;

    @GetMapping
    public ResponseEntity<List<ProdutoDetalhamentoDto>> listarProdutos(){
        List<ProdutoDetalhamentoDto  > produtos = produtoService.listarProdutos();

        if(produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetalhamentoDto> listarProdutoPorId(@PathVariable Long id){
        Produto produto = produtoService.buscarPorId(id);
        ProdutoDetalhamentoDto dto = new ProdutoDetalhamentoDto(produto);

        dto.setImagens(produto.getImagens().stream()
                .map(imagem -> imagemService.converterParaBase64(imagem.getNomeImagem()))
                .toList());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDetalhamentoDto> cadastrarProduto(@Valid @RequestBody CadastroProdutoDto produto){
        Produto produtoCadastrado = produtoService.cadastrarProduto(produto);
        ProdutoDetalhamentoDto dto = new ProdutoDetalhamentoDto(produtoCadastrado);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDetalhamentoDto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody CadastroProdutoDto produto){
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        ProdutoDetalhamentoDto dto = new ProdutoDetalhamentoDto(produtoAtualizado);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> statusProduto(@PathVariable Long id, @RequestParam boolean status){
        produtoService.statusProduto(status, id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/promocao/{id}")
    public ResponseEntity<Void> statusPromocaoProduto(@PathVariable Long id, @RequestParam boolean status){
        produtoService.statusPromocaoProduto(id, status);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> produtoPorEstabelecimento(@PathVariable Long id){
        List<Produto> produtos = produtoService.produtoPorEstabelecimento(id);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoDetalhamentoDto::new).toList();

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/pesquisa")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> pesquisa(@RequestParam String pesquisa){
        List<Produto> produtos = produtoService.barraDePesquisa(pesquisa);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoDetalhamentoDto::new).toList();

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/promocao")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> produtoEmPromocao(){
        List<Produto> produtos = produtoService.produtoEmPromocao();
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoDetalhamentoDto::new).toList();

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/download-csv")
    public ResponseEntity<byte[]> downloadCsv(@RequestParam("idEmpresa") Long id) {
        byte[] data = produtoService.downloadCsv(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=produtos.csv");
        headers.add("Content-Type", "text/csv; charset=UTF-8");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(data);
    }


    @PostMapping("/upload-csv")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> uploadCsv(@RequestParam("arquivo") MultipartFile file, @RequestParam("secao")String secao){
        if(file.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        List<Produto> produtos = produtoService.uploadCsv(file, secao);
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoDetalhamentoDto::new).toList();

        return ResponseEntity.status(201).body(dtos);
    }
}
