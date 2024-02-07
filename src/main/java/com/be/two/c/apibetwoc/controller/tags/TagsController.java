package com.be.two.c.apibetwoc.controller.tags;


import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.service.tag.TagService;
import com.be.two.c.apibetwoc.service.tag.dto.TagResponseDTO;
import com.be.two.c.apibetwoc.service.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {


    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponseDTO>> listarTags() {
        List<Tag> tags = tagService.listar();
        return ResponseEntity.ok(tags.stream().map(TagMapper::toTagResponse).toList());
    }


}
