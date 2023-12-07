package com.be.two.c.apibetwoc.service.produto.mapper;
import com.be.two.c.apibetwoc.controller.tag.TagDTO;
import com.be.two.c.apibetwoc.model.Tag;

public class TagMapper {

    public static Tag toTag(TagDTO tagDto){
        Tag tag = new Tag();
        tag.setDescricao(tagDto.getNome());
        tag.setId(tagDto.getId());
        return tag;
    }
}
