package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.CarrinhoDTO;
import com.be.two.c.apibetwoc.model.Carrinho;
import com.be.two.c.apibetwoc.service.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {
    @Autowired
    private CarrinhoService carrinhoService;
    @GetMapping("/{id}")
    public ResponseEntity<List<Carrinho>> carrinhoDoConsumidor(@PathVariable Long id){
        List<Carrinho> carrinho = carrinhoService.carrinhoDoConsumidor(id);
        if (carrinho.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carrinho);
    }
    @PostMapping
    public ResponseEntity<Carrinho> adicionandoProduto(@Valid @RequestBody CarrinhoDTO carrinho){
        LocalDateTime dtH = LocalDateTime.now();
        return ResponseEntity.ok(carrinhoService.adicionar(carrinho,dtH));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> editarProduto(@PathVariable Long id, @RequestParam Integer quantidade){
        carrinhoService.editar(id,quantidade);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id){
        carrinhoService.removerProduto(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/esvaziar-carrinho/{id}")
    public ResponseEntity<Void> esvaziarCarrinho(@PathVariable Long id){
        carrinhoService.esvaziarCarrinho(id);
        return ResponseEntity.noContent().build();
    }
}
