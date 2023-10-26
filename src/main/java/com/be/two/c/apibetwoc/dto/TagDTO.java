package com.be.two.c.apibetwoc.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagDTO {
    @Nullable
    private Long id;
    @NotBlank
    private String descricao;
}
