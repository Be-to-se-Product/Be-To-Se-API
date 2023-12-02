package com.be.two.c.apibetwoc.controller.tag;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    @Nullable
    private Long id;
    @NotBlank
    private String descricao;
}
