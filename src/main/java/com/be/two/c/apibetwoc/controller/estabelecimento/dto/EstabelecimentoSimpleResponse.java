package com.be.two.c.apibetwoc.dto.estabelecimento;

import com.be.two.c.apibetwoc.dto.MetodoPagamentoAceitoResponseDto;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoSimpleResponse {
    private Long id;
    private String nome;
    private List<Long> idMetodo;

    public EstabelecimentoSimpleResponse(Estabelecimento estabelecimento) {
        this.id = estabelecimento.getId();
        this.nome = estabelecimento.getNome();
        this.idMetodo = getIdMetodo();
    }
}
