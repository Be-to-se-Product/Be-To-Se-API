package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(){
        List<Produto> produtos = produtoService.listarProdutos();

        if(produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutoPorId(@PathVariable Long id){
        return ResponseEntity.status(200).body(produtoService.listarProdutoPorId(id));
    }

    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<List<Produto>> listarProdutosPorEstabelecimento(@PathVariable Long id){
        List<Produto> produtos = produtoService.listarProdutosPorEstabelecimento(id);

        return produtos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/download-csv")
    public ResponseEntity<byte[]> downloadCsv(@RequestParam("idEmpresa") Long id){
        return ResponseEntity.status(200).body(produtoService.downloadCsv(id));
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody CadastroProdutoDto produto){
        return ResponseEntity.status(201).body(produtoService.cadastrarProduto(produto));
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<List<Produto>> uploadCsv(@RequestParam("arquivo")MultipartFile file, @RequestParam("secao")String secao){
        if(file.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body(produtoService.uploadCsv(file, secao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody CadastroProdutoDto produto){
        return ResponseEntity.status(201).body(produtoService.atualizarProduto(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.status(204).build();
    }

}
