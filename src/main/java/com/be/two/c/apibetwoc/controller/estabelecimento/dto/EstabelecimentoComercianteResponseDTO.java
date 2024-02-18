package com.be.two.c.apibetwoc.controller.estabelecimento.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoComercianteResponseDTO {
    private Long id;
    private String nome;
    private String segmento;
    private List<String> imagens;
    private Integer pedidosPendentes;
    private Integer produtosAtivos;
}
