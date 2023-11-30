package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    void deleteByIdIn(List<Long> idItemVenda);
}
