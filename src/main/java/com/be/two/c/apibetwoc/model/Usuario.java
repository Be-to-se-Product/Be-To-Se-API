package com.be.two.c.apibetwoc.model;

import com.be.two.c.apibetwoc.model.observer.Assinante;
import com.be.two.c.apibetwoc.service.EnviadorEmailService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Usuario implements Assinante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Override
    public void receberNewsletter(EnviadorEmailService enviadorEmailService, Newsletter newsletter) {
        enviadorEmailService.sendEmail("easyfind@master.com", this.email, newsletter.getTitulo(), newsletter.getConteudo());
    }
}
