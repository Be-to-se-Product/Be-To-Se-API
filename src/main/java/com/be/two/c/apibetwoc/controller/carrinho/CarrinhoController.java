package com.be.two.c.apibetwoc.controller.carrinho;

import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoRequestDTO;
import com.be.two.c.apibetwoc.controller.carrinho.dto.CarrinhoResponseDTO;
import com.be.two.c.apibetwoc.controller.carrinho.mapper.CarrinhoMapper;
import com.be.two.c.apibetwoc.controller.produto.dto.ProdutoDetalhamentoDto;
import com.be.two.c.apibetwoc.controller.produto.mapper.ProdutoMapper;
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
    @GetMapping
    public ResponseEntity<List<CarrinhoResponseDTO>> carrinhoDoConsumidor(@RequestParam Long id){
        List<Carrinho> carrinho = carrinhoService.carrinhoDoConsumidor(id);
        if (carrinho.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<CarrinhoResponseDTO> dtos = carrinho.stream().map(CarrinhoMapper::toDto).toList();

        return ResponseEntity.ok(dtos);
    }
    @PostMapping
    public ResponseEntity<CarrinhoResponseDTO> adicionandoProduto(@Valid @RequestBody CarrinhoRequestDTO carrinhoDto){
        LocalDateTime dtH = LocalDateTime.now();
        Carrinho carrinho = carrinhoService.adicionar(carrinhoDto,dtH);
        CarrinhoResponseDTO dto = CarrinhoMapper.toDto(carrinho);
        return ResponseEntity.ok(dto);
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
