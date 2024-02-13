package com.be.two.c.apibetwoc.controller.tag;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Getter
@Setter

@Data

@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    @NotNull
    private Long id;
}
