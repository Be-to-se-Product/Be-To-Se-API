package com.be.two.c.apibetwoc.model;


import com.be.two.c.apibetwoc.service.EnviadorEmailService;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToOne(mappedBy = "usuario")
    private Consumidor consumidor;

    @OneToOne(mappedBy = "usuario")
    private Comerciante comerciante;

}
