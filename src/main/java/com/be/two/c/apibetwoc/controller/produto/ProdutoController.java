package com.be.two.c.apibetwoc.controller.produto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.mapa.ProdutoMapaResponseDTO;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.arquivo.ArquivoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoReponseDTO;
import com.be.two.c.apibetwoc.service.produto.ProdutoMapaService;
import com.be.two.c.apibetwoc.service.produto.ProdutoService;


import com.be.two.c.apibetwoc.util.TipoArquivo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ArquivoService arquivoService;
    private final ProdutoMapaService produtoMapaService;
    private final HttpServletRequest request;

        @GetMapping
        public ResponseEntity<List<ProdutoDetalhamentoDto>> listarProdutos(){
            List<Produto> produtos = produtoService.listarProdutos();

            if(produtos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList());
        }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetalhamentoDto> listarProdutoPorId(@PathVariable Long id){
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ProdutoMapper.toProdutoDetalhamento(produto));
    }

    @PostMapping
    public ResponseEntity<ProdutoDetalhamentoDto> cadastrarProduto(@Valid @RequestPart CadastroProdutoDto produto, @RequestPart List<MultipartFile> imagens ){
        Produto produtoCadastrado = produtoService.cadastrarProduto(produto);
        List<Imagem> imagensCadastradas = imagens.stream().map(element-> produtoService.cadastrarImagensProduto(element,TipoArquivo.IMAGEM,produtoCadastrado)).toList();
        String dominio = request.getRequestURL().toString().replace(request.getRequestURI(), "/");
        imagensCadastradas.forEach(element-> element.setNomeReferencia(dominio +""+ element.getNomeReferencia()));
        produtoCadastrado.setImagens(imagensCadastradas);



        return ResponseEntity.ok(ProdutoMapper.toProdutoDetalhamento(produtoCadastrado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDetalhamentoDto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody CadastroProdutoDto produto){
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
//        ProdutoDetalhamentoDto dto = new ProdutoDetalhamentoDto(produtoAtualizado,);

        return ResponseEntity.ok(new ProdutoDetalhamentoDto());
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
         List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList();

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/pesquisa")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> pesquisa(@RequestParam String pesquisa){
        List<Produto> produtos = produtoService.barraDePesquisa(pesquisa);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList();

        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/promocao")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> produtoEmPromocao(){
        List<Produto> produtos = produtoService.produtoEmPromocao();
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList();

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
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList();
        return ResponseEntity.status(201).body(dtos);
    }

    @GetMapping("/mapa")
    public ResponseEntity<List<ProdutoMapaResponseDTO>> listarProdutos(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double distancia,  @RequestParam(required = false)  String nome, @RequestParam(required = false) String metodoPagamento){
        List<Produto> produtos = produtoMapaService.retornarProdutos(latitude, longitude, distancia, nome,metodoPagamento);
        System.out.println(metodoPagamento);
        if(produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toProdutoMapaReponse).toList());
    }




}
