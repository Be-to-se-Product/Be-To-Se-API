package com.be.two.c.apibetwoc.service.tag.mapper;

import com.be.two.c.apibetwoc.model.Tag;
import com.be.two.c.apibetwoc.service.tag.dto.TagResponseDTO;

public class TagMapper {

    public static TagResponseDTO toTagResponse(Tag tag){
        TagResponseDTO tagResponseDTO = new TagResponseDTO();
        tagResponseDTO.setId(tag.getId());
        tagResponseDTO.setNome(tag.getDescricao());
        return tagResponseDTO;
    }
}
