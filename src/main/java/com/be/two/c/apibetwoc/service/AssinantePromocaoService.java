package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.dto.AssinantePromocaoDto;
import com.be.two.c.apibetwoc.model.AssinantePromocao;
import com.be.two.c.apibetwoc.repository.AssinantePromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class AssinantePromocaoService {
    @Autowired
    private AssinantePromocaoRepository assinPromoRepository;


    public ResponseEntity<List<AssinantePromocao>> listarTodos() {
        List<AssinantePromocao> assinantes = assinPromoRepository.findAll();
        if (assinantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(assinantes);
    }

    public ResponseEntity<AssinantePromocao> cadastrarNovo(@RequestBody AssinantePromocaoDto assinantePromocao) {
        AssinantePromocao assinantePromo = new AssinantePromocao(null,assinantePromocao.getNome(),assinantePromocao.getEmail(),assinantePromocao.getTelefone());
        assinPromoRepository.save(assinantePromo);
        return ResponseEntity.ok().body(assinantePromo);
    }

}
