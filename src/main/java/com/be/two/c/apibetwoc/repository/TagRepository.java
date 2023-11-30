package com.be.two.c.apibetwoc.repository;

import com.be.two.c.apibetwoc.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository <Tag, Long>{
    List<Tag> findByIdIn(List<Long> tagsId);

}
