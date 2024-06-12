package com.be.two.c.apibetwoc.controller.produto.dto.mapa;

import com.be.two.c.apibetwoc.model.Imagem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCarrinhoResponseDTO {
    private Long id;
    private String nome;
    private Double preco;
    private List<String> imagens;

}
