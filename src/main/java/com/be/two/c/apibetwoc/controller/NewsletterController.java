package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.model.AssinanteEmail;
import com.be.two.c.apibetwoc.model.Newsletter;
import com.be.two.c.apibetwoc.service.NewsletterService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/newsletter")
public class NewsletterController {

    @Autowired
    private NewsletterService newsletterService;


    @GetMapping
    public ResponseEntity<List<Newsletter>> listar() {
        List<Newsletter> conteudos = this.newsletterService.listar();

        if (conteudos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(conteudos);
    }

    @GetMapping("/{idNewsletter}")
    public ResponseEntity<Newsletter> buscarPorId(@PathVariable UUID idNewsletter) {
        return ResponseEntity.status(200).body(this.newsletterService.buscarPorId(idNewsletter));
    }

    @PostMapping
    public ResponseEntity<Newsletter> criar(@RequestBody Newsletter newsletter) {
        this.newsletterService.criar(newsletter);
        return ResponseEntity.status(201).body(newsletter);
    }

    @PostMapping("/{idNewsletter}/assinantes")
    public ResponseEntity<Void> inscrever(
            @PathVariable UUID idNewsletter,
            @RequestBody AssinanteEmail assinanteEmail
    ) {
        this.newsletterService.adicionarAssinante(idNewsletter, assinanteEmail);
        return ResponseEntity.status(201).build();
    }

//    @PostMapping("/{idNewsletter}/publicacao")
//    public ResponseEntity<Void> publicar(@PathVariable UUID idNewsletter) {
//        this.newsletterService.publicarNewsletter(idNewsletter);
//        return ResponseEntity.status(200).build();
//    }
}
