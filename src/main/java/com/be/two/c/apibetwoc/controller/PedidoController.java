package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PatchMapping("{idPedido}/status")
    public ResponseEntity<Void> atualizarStatusPedido(@PathVariable Long idPedido, @RequestBody String status){
        pedidoService.alterarStatusPedido(idPedido, status);
        return ResponseEntity.noContent().build();
    }
}
