package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.controller.pedido.dto.*;

import com.be.two.c.apibetwoc.controller.pedido.mapper.PedidoMapper;
import com.be.two.c.apibetwoc.controller.usuario.dto.UsuarioDetalhes;
import com.be.two.c.apibetwoc.exception.ForbidenPedidoException;
import com.be.two.c.apibetwoc.exception.NaoAutorizadoException;
import com.be.two.c.apibetwoc.infra.EntidadeNaoExisteException;
import com.be.two.c.apibetwoc.model.*;
import com.be.two.c.apibetwoc.repository.EstabelecimentoRepository;
import com.be.two.c.apibetwoc.repository.MetodoPagamentoAceitoRepository;
import com.be.two.c.apibetwoc.repository.PedidoRepository;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import com.be.two.c.apibetwoc.util.ListaObj;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final MetodoPagamentoAceitoRepository metodoPagamentoAceitoRepository;
    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository repository;
    private final TransacaoService transacaoService;
    private void validarPedidosEstabelecimento(Long id){
        Optional<Estabelecimento> estabelecimentoOpt=  estabelecimentoRepository.findById(id);
        if(estabelecimentoOpt.isEmpty()){
            throw new EntidadeNaoExisteException("Estabelecimento não encontrado");
        }
        Estabelecimento estabelecimento = estabelecimentoOpt.get();
        if(estabelecimento.getComerciante().getUsuario().getId() != autenticacaoService.loadUsuarioDetails().getId()){
            throw new ForbidenPedidoException("Estabelecimento não encontrado");
        }
    }
    public void alterarStatusPedido(Long idPedido, PedidoDtoStatus novoStatus) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new EntidadeNaoExisteException("O pedido informado não existe"));
        validarPedidosEstabelecimento(pedido.getMetodoPagamentoAceito().getEstabelecimento().getId());
        pedido.setStatusDescricao(novoStatus.getStatus());
        pedidoRepository.save(pedido);
    }
    public Pedido cadastrar(@Valid MetodoDto metodoDto){
        Pedido pedido = PedidoMapper.of(metodoDto);
        MetodoPagamentoAceito metodoPagamentoAceito = metodoPagamentoAceitoRepository.findById(metodoDto.getIdMetodoPagamento())
                .orElseThrow(() -> new EntidadeNaoExisteException("Método de pagamento informado não existe"));
        pedido.setMetodoPagamentoAceito(metodoPagamentoAceito);
        pedido = pedidoRepository.save(pedido);
        Transacao transacao = transacaoService.adicionar(pedido);
        return pedido;
    }
    public List<ResponsePedidoConsumidorDto> listarPorConsumidor(StatusPedido status) {
        if(autenticacaoService.loadUsuarioDetails()==null){
                throw new NaoAutorizadoException();
        }

        if(status != null){
            UsuarioDetalhes usuarioDetalhes = autenticacaoService.loadUsuarioDetails();
            Usuario usuario =  repository.findById(usuarioDetalhes.getId()).get();
            return pedidoRepository.searchByConsumidorEStatus(usuario.getConsumidor().getId(), status).stream().map(PedidoMapper::ofResponseUsuario).toList();
        }

        List <Pedido> pedidos = pedidoRepository.searchByConsumidor(autenticacaoService.loadUsuarioDetails().getId());
        return pedidos.stream().map(PedidoMapper::ofResponseUsuario).toList();
    }
    public ListaObj<ResponsePedidoDTO> listarPorEstabelecimento(Long idEstabelecimento) {

        validarPedidosEstabelecimento(idEstabelecimento);
            List<Pedido> pedidos = pedidoRepository.searchByEstabelecimento(idEstabelecimento);
            ListaObj<ResponsePedidoDTO> listaPedidos = new ListaObj<>(pedidos.size());
            for (Pedido pedido : pedidos) {
                listaPedidos.adiciona(PedidoMapper.of(pedido));
            }

            return listaPedidos;

    }
    public List<ResponsePedidoDTO> listarPorEstabelecimentoEStatus(Long idEstabelecimento, StatusPedido status) {
        validarPedidosEstabelecimento(idEstabelecimento);
       return  pedidoRepository.searchByEstabelecimentoEStatus(idEstabelecimento, status).stream().map(PedidoMapper::of).toList();
    }
    public void deletar(Long id) {
    Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoExisteException("Pedido não encontrado"));
    pedido.setStatusDescricao(StatusPedido.CANCELADO);
    }
}