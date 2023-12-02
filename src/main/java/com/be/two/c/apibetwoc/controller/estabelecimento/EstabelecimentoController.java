package com.be.two.c.apibetwoc.controller.estabelecimento;

import com.be.two.c.apibetwoc.controller.estabelecimento.dto.*;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.service.AgendaService;
import com.be.two.c.apibetwoc.service.EstabelecimentoService;
import com.be.two.c.apibetwoc.service.MetodoPagamentoAceitoService;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
@RequiredArgsConstructor
public class EstabelecimentoController {

    private final EstabelecimentoService estabelecimentoService;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final AgendaService agendaService;
    private final ImagemService imagemService;

    @GetMapping
    public ResponseEntity<List<ResponseEstabelecimentoDto>> listarTodos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarTodos();

        return estabelecimentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(estabelecimentos.stream().map(EstabelecimentoMapper::toResponseEstabelecimento).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEstabelecimentoDto> listarPorId(@PathVariable Long id){
        return ResponseEntity.status(200).body(EstabelecimentoMapper.toResponseEstabelecimento(estabelecimentoService.listarPorId(id)));
    }

    @GetMapping("/segmento")
    public ResponseEntity<List<ResponseEstabelecimentoDto>> listarPorSegmento(@RequestParam String segmento){
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarPorSegmento(segmento);

        return estabelecimentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(estabelecimentos.stream().map(EstabelecimentoMapper::toResponseEstabelecimento).toList());
    }

    @PostMapping
    public ResponseEntity<ResponseEstabelecimentoDto> cadastrarEstabelecimento(@Valid @RequestBody CadastroEstabelecimentoDto estabelecimento) {
        Estabelecimento estabelecimentoCriado = estabelecimentoService.cadastroEstabelecimento(estabelecimento);
        return ResponseEntity.status(201).body(EstabelecimentoMapper.toResponseEstabelecimento(estabelecimentoCriado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEstabelecimentoDto> atualizarEstabelecimento(@Valid @RequestBody AtualizarEstabelecimentoDto estabelecimentoDto, @PathVariable Long id) {
        Estabelecimento estabelecimentoAtualizado = estabelecimentoService.atualizarEstabelecimento(estabelecimentoDto, id);

        return ResponseEntity.status(200).body(EstabelecimentoMapper.toResponseEstabelecimento(estabelecimentoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstabelecimento(@PathVariable Long id){
        estabelecimentoService.deletar(id);
        return ResponseEntity.status(204).build();
    }


    @GetMapping("/rota-pessoa")
    public Long calcularRotaPessoa(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaPessoa(coordenadaDto);
    }

    @GetMapping("/rota-bicicleta")
    public Long calcularRotaBicicleta(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaBicicleta(coordenadaDto);
    }

    @GetMapping("/rota-carro")
    public Long calcularRotaCarro(@Valid @RequestBody CoordenadaDto coordenadaDto) {
        return estabelecimentoService.calcularRotaCarro(coordenadaDto);
    }

}
