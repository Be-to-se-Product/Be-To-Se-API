package com.be.two.c.apibetwoc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagDTO {
    private Long id;
    @NotBlank
    private String descricao;
}
