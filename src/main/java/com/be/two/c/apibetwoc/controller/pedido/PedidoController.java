package com.be.two.c.apibetwoc.controller.pedido;

import com.be.two.c.apibetwoc.controller.pedido.dto.*;
import com.be.two.c.apibetwoc.controller.pedido.mapper.PedidoMapper;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.service.ItemVendaService;
import com.be.two.c.apibetwoc.service.pedido.PedidoService;
import com.be.two.c.apibetwoc.util.ListaObj;
import com.be.two.c.apibetwoc.model.StatusPedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final ItemVendaService itemVendaService;

    @PostMapping
    public ResponseEntity<ResponsePedidoDTO> cadastrar(@RequestBody @Valid PedidoCreateDto pedidoCreate){
        Pedido pedido = itemVendaService.cadastrar(pedidoCreate);
        return ResponseEntity.ok(PedidoMapper.of(pedido));
    }

    @PatchMapping("{idPedido}/status")
    public ResponseEntity<Void> atualizarStatusPedido(@PathVariable Long idPedido, @RequestBody PedidoDtoStatus status) {
        pedidoService.alterarStatusPedido(idPedido, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consumidor")
    public ResponseEntity<List<ResponsePedidoConsumidorDto>> buscarPedidosPorConsumidor(@RequestParam(required = false) StatusPedido status) {
        List<ResponsePedidoConsumidorDto> pedidos = pedidoService.listarPorConsumidor(status);
        if (pedidos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estabelecimento/{idEstabelecimento}")
    public ResponseEntity<ListaObj<ResponsePedidoDTO>> buscarPedidosPorEstabelecimento(@PathVariable Long idEstabelecimento) {
        ListaObj<ResponsePedidoDTO> pedidos = pedidoService.listarPorEstabelecimento(idEstabelecimento);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estabelecimento/{idEstabelecimento}/status")
    public ResponseEntity<List<ResponsePedidoDTO>> buscarPedidosPorEstabelecimentoEStatus(@PathVariable Long idEstabelecimento, @RequestParam StatusPedido status) {
        List<ResponsePedidoDTO> pedidos = pedidoService.listarPorEstabelecimentoEStatus(idEstabelecimento, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}

