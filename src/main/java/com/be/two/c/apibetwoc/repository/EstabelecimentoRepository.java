package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Estabelecimento;
import com.be.two.c.apibetwoc.model.Produto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    Estabelecimento findById(long id);
    List<Estabelecimento> findBySegmento(String segmento);


    @Query("SELECT p.id FROM Estabelecimento e " +
            "JOIN e.secao s " +
            "JOIN s.produto p  " +
            "WHERE (6371 * acos(cos(radians(?1)) * cos(radians(e.endereco.geolocalizacaoX)) * cos(radians(e.endereco.geolocalizacaoY) - radians(?2)) + sin(radians(?1)) * sin(radians(e.endereco.geolocalizacaoX)))) < ?3")
    List<Integer> buscarPorLocalizacao(Double latitude, Double longitude, Double distancia);


    List<Estabelecimento> findByComercianteUsuarioId(Long usuarioId);
}
