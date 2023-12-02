package com.be.two.c.apibetwoc.controller.tag;
import com.be.two.c.apibetwoc.model.Tag;

public class TagMapper {
    public static TagDTO of(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setDescricao(tag.getDescricao());
        return dto;
    }

    public static Tag of(TagDTO dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setDescricao(dto.getDescricao());
        return tag;
    }
}
