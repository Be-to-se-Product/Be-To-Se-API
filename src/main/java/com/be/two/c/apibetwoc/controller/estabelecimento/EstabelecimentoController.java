package com.be.two.c.apibetwoc.controller.estabelecimento;

import com.be.two.c.apibetwoc.controller.estabelecimento.dto.*;
import com.be.two.c.apibetwoc.controller.estabelecimento.mapper.EstabelecimentoMapper;
import com.be.two.c.apibetwoc.controller.metodoPagamento.dto.MetodoPagamentoResponseDTO;
import com.be.two.c.apibetwoc.controller.metodoPagamento.mapper.MetodoPagamentoAceitoMapper;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.MetodoPagamentoAceito;
import com.be.two.c.apibetwoc.model.Pedido;
import com.be.two.c.apibetwoc.service.EstabelecimentoService;
import com.be.two.c.apibetwoc.service.MetodoPagamentoAceitoService;
import com.be.two.c.apibetwoc.service.SecaoService;
import com.be.two.c.apibetwoc.service.imagem.ImagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/estabelecimentos")
@RequiredArgsConstructor
public class EstabelecimentoController {
    private final EstabelecimentoService estabelecimentoService;
    private final ImagemService imagemService;
    private final MetodoPagamentoAceitoService metodoPagamentoAceitoService;
    private final SecaoService secaoService;

    @GetMapping("/comerciante")
    public ResponseEntity<List<EstabelecimentoComercianteResponseDTO>> listarPorComerciante(){
         List<Estabelecimento> estabelecimentos = estabelecimentoService.listarPorComerciante();
         if(estabelecimentos.isEmpty())return ResponseEntity.noContent().build();
        return ResponseEntity.ok(estabelecimentos.stream().map(e->EstabelecimentoMapper.toResponseEstabelecimentoComerciante(e,10,10)).toList());
    }
    @GetMapping
    public ResponseEntity<List<EstabelecimentoResponseDTO>> listarTodos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarTodos();

        return estabelecimentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(estabelecimentos.stream().map(EstabelecimentoMapper::toResponseEstabelecimento).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDTO> listarPorId(@PathVariable Long id){
        return ResponseEntity.status(200).body(EstabelecimentoMapper.toResponseEstabelecimento(estabelecimentoService.listarPorId(id)));
    }

    @GetMapping("/segmento")
    public ResponseEntity<List<EstabelecimentoResponseDTO>> listarPorSegmento(@RequestParam String segmento){
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarPorSegmento(segmento);

        return estabelecimentos.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(estabelecimentos.stream().map(EstabelecimentoMapper::toResponseEstabelecimento).toList());
    }

    @PostMapping
    public ResponseEntity<EstabelecimentoComercianteResponseDTO> cadastrarEstabelecimento(@Valid @RequestBody EstabelecimentoCadastroDTO estabelecimento) {
        Estabelecimento estabelecimentoCriado = estabelecimentoService.cadastroEstabelecimento(estabelecimento);
        secaoService.cadastrarSecoes(estabelecimento.getSecao(),estabelecimentoCriado);
        return ResponseEntity.status(201).body(EstabelecimentoMapper.toResponseEstabelecimentoComerciante(estabelecimentoCriado,10,10));
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<Void> salvarImagem(@RequestParam MultipartFile imagem, @PathVariable Long id){
        estabelecimentoService.salvarImagem(imagem,id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoResponseDTO> atualizarEstabelecimento(@Valid @RequestBody EstabelecimentoAtualizarDTO estabelecimentoDto, @PathVariable Long id) {
        Estabelecimento estabelecimentoAtualizado = estabelecimentoService.atualizarEstabelecimento(estabelecimentoDto, id);
        return ResponseEntity.status(200).body(EstabelecimentoMapper.toResponseEstabelecimento(estabelecimentoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstabelecimento(@PathVariable Long id){
        estabelecimentoService.deletar(id);
        return ResponseEntity.status(204).build();
    }


    @GetMapping("/metodos/{id}")
    public ResponseEntity<List<MetodoPagamentoResponseDTO>> metodosAceitos(@PathVariable Long id){
        List<MetodoPagamentoAceito> metodos = metodoPagamentoAceitoService.findByEstabelecimentoId(id);
        if (metodos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        List<MetodoPagamentoResponseDTO> metodosAceito = MetodoPagamentoAceitoMapper.of(metodos);

        return ResponseEntity.ok(metodosAceito);
    }
    
}
