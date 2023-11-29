package com.be.two.c.apibetwoc.service;

import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> listarTodos() {
        return tagRepository.findAll();
    }
}
