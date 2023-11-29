package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.controller.tag.TagDTO;
import com.be.two.c.apibetwoc.controller.tag.TagMapper;
import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDTO>> listarTodos() {
        List<Tag> tags = tagService.listarTodos();

        List<TagDTO> dtos = tags.stream()
                .map(TagMapper::of)
                .toList();

        return tags.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(dtos);
    }
}
