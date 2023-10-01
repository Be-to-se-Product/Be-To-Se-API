package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.dto.CadastroEstabelecimentoDto;
import com.be.two.c.apibetwoc.model.Comerciante;
import com.be.two.c.apibetwoc.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    Estabelecimento findByCnpj(Integer id);

}
