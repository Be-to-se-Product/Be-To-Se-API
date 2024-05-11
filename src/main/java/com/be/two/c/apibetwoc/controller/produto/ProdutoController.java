package com.be.two.c.apibetwoc.controller.produto;

import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoVendaDto;
import com.be.two.c.apibetwoc.controller.produto.dto.mapa.ProdutoMapaResponseDTO;
import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoVendaResponseDto;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
import com.be.two.c.apibetwoc.model.Imagem;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.arquivo.ArquivoService;
import com.be.two.c.apibetwoc.service.arquivo.dto.ArquivoSaveDTO;
import com.be.two.c.apibetwoc.service.produto.ProdutoMapaService;
import com.be.two.c.apibetwoc.service.produto.ProdutoService;


import com.be.two.c.apibetwoc.util.FilaRequisicao;
import com.be.two.c.apibetwoc.util.PilhaObj;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapaService produtoMapaService;
    public FilaRequisicao filaRequisicao = new FilaRequisicao();

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
        ProdutoDetalhamentoDto produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDetalhamentoDto> cadastrarProduto(@RequestBody CadastroProdutoDto produto)  {
                Produto produtoCadastrado = produtoService.cadastrarProduto(produto);
                URI uri = ServletUriComponentsBuilder.fromPath("{id}").buildAndExpand(produtoCadastrado.getId()).toUri();
                return ResponseEntity.created(uri).body(ProdutoMapper.toProdutoDetalhamento(produtoCadastrado));
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<Void> cadastrarImagens(@RequestParam List<MultipartFile> imagens, @PathVariable Long id){
            produtoService.cadastrarImagens(imagens,id);
            return ResponseEntity.created(null).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDetalhamentoDto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody CadastroProdutoDto produto){
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.ok(ProdutoMapper.toProdutoDetalhamento(produtoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarProduto(@PathVariable Long id){
        produtoService.inativarProduto(id);
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

        return dtos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(dtos);
    }

    @GetMapping("/pesquisa/{id}")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> pesquisa(@PathVariable Long id, @RequestParam String pesquisa){
        List<Produto> produtos = produtoService.barraDePesquisa(id, pesquisa);
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
    public ResponseEntity<List<ProdutoDetalhamentoDto>> uploadCsv(@RequestParam("arquivo") MultipartFile file, @RequestParam("secao")Long secaoId){
        if(file.isEmpty()){
            return ResponseEntity.status(400).build();
        }
        List<Produto> produtos = produtoService.uploadCsv(file, secaoId);
        List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList();

        return ResponseEntity.status(201).body(dtos);
    }

    @GetMapping("/mapa")
    public ResponseEntity<List<ProdutoMapaResponseDTO>> listarProdutos(@RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double distancia,  @RequestParam(required = false)  String nome, @RequestParam(required = false) String metodoPagamento){
        List<Produto> produtos = produtoMapaService.retornarProdutos(latitude, longitude, distancia, nome,metodoPagamento);
        if(produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos.stream().map(element->ProdutoMapper.toProdutoMapaReponse(element,latitude,longitude)).toList());
    }
    @PostMapping("/venda")
    public ResponseEntity<List<ProdutoVendaResponseDto>> listaProdutoVenda(@RequestBody List<ProdutoVendaDto> produtos){
        List<Long> ids = produtos.stream().map(ProdutoVendaDto::getIdProduto).collect(Collectors.toList());
        List<Produto> produtosExistentes = produtoService.buscarProdutosParaVenda(ids);

        if (produtosExistentes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<ProdutoVendaResponseDto> dtos = new ArrayList<>();
        for (ProdutoVendaDto produto: produtos){
            ProdutoVendaResponseDto dto = ProdutoMapper.toprodutoVendaResponse(produto);
            for(Produto entidade:produtosExistentes){
                if (entidade.getId().equals(dto.getId())){
                    dto.setNome(entidade.getNome());
                    dto.setPreco(entidade.getPreco());
                    dto.setIdEstabelecimento(entidade.getSecao().getEstabelecimento().getId());
                }
            }
            dtos.add(dto);
        }

        return ResponseEntity.ok(dtos);
    }
    @PostMapping("/upload-txt")
    public ResponseEntity<List<ProdutoDetalhamentoDto>> uploadTxt(@RequestParam("arquivo") MultipartFile file, @RequestParam("secao") Long secao){
            if (file.isEmpty()){
                return ResponseEntity.status(400).build();
            }
            List<Produto> produtos = produtoService.uploadTxt(file, secao);
            List<ProdutoDetalhamentoDto> dtos = produtos.stream().map(ProdutoMapper::toProdutoDetalhamento).toList();
            return ResponseEntity.status(201).body(dtos);
    }


    @GetMapping("/mobile/{id}")
    public ResponseEntity<ProdutoMapaResponseDTO> retornarProdutosTelaMapa(@PathVariable Long id, @RequestParam Double latitude,@RequestParam Double longitude){

        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(ProdutoMapper.toProdutoMobile(produto,latitude,longitude));

    }

}
