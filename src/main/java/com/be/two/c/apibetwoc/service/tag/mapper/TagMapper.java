package com.be.two.c.apibetwoc.service.tag.mapper;
import com.be.two.c.apibetwoc.controller.tag.TagDTO;
import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.service.tag.dto.TagResponseDTO;

public class TagMapper {

    public static TagResponseDTO toTagResponse(Tag tag){
        TagResponseDTO tagResponseDTO = new TagResponseDTO();
        tagResponseDTO.setId(tag.getId());
        System.out.println(tag.getDescricao());
        tagResponseDTO.setNome(tag.getDescricao());
        System.out.println(tagResponseDTO.getNome());
        return tagResponseDTO;
    }

    public static Tag toTag(TagDTO tagDto){
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        return tag;
    }
}
