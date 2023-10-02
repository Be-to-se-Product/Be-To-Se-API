package com.be.two.c.apibetwoc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class AssinantePromocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;

}
