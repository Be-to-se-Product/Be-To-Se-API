package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.produto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.dto.produto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDetalhamentoDto>> listarProdutos(){
        List<ProdutoDetalhamentoDto  > produtos = produtoService.listarProdutos();

        if(produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutoPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody CadastroProdutoDto produto){
        return ResponseEntity.ok(produtoService.cadastrarProduto(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody CadastroProdutoDto produto){
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produto));
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
    public ResponseEntity<List<Produto>> produtoPorEstabelecimento(@PathVariable Long id){
        List<Produto> produtos = produtoService.produtoPorEstabelecimento(id);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/pesquisa")
    public ResponseEntity<List<Produto>> pesquisa(@RequestParam String pesquisa){
        List<Produto> produtos = produtoService.barraDePesquisa(pesquisa);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/promocao")
    public ResponseEntity<List<Produto>> produtoEmPromocao(){
        List<Produto> produtos = produtoService.produtoEmPromocao();
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/download-csv")
    public ResponseEntity<byte[]> downloadCsv(@RequestParam("idEmpresa") Long id){
        return ResponseEntity.status(200).body(produtoService.downloadCsv(id));
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<List<Produto>> uploadCsv(@RequestParam("arquivo") MultipartFile file, @RequestParam("secao")String secao){
        if(file.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body(produtoService.uploadCsv(file, secao));
    }
}
