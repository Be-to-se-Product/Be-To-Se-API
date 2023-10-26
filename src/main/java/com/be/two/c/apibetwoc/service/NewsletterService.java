package com.be.two.c.apibetwoc.service;


import com.be.two.c.apibetwoc.model.Newsletter;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.model.Usuario;
import com.be.two.c.apibetwoc.model.observer.Assinante;
import com.be.two.c.apibetwoc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NewsletterService {

    @Autowired
    private EnviadorEmailService enviadorEmailService;
    @Autowired
    private UsuarioRepository userRepo;

    private List<Newsletter> newsletters = new ArrayList<>();

    public void  publicarNewsletter(Produto produto) {
        List<Usuario> listarusuarios = userRepo.findAll();
        String produtonome= produto.getNome();
        String estabelecimento= produto.getSecao().getEstabelecimento().getNome();
        String produtoformat= String.format("O Produto %s foi cadastrado no estabelecimento %s \n Compareça em até 10 dias e receberá 10 por cento de desconto!",produtonome,estabelecimento);
        Newsletter newsletter = new Newsletter("Produto Novo Adicionado",produtoformat);
        listarusuarios.forEach(usuario -> usuario.receberNewsletter(enviadorEmailService,newsletter));

    }

    public void adicionarAssinante(UUID idNewsletter, Assinante assinante) {
        Newsletter newsletter = this.buscarPorIndice(idNewsletter);
        newsletter.inscrever(assinante);
    }

    public void criar(Newsletter newsletter) {
        this.newsletters.add(newsletter);
    }

    public List<Newsletter> listar() {
        return newsletters;
    }

    public Newsletter buscarPorId(UUID idNewsletter) {
        return buscarPorIndice(idNewsletter);
    }

    private Newsletter buscarPorIndice(UUID id) {
        return this.newsletters.stream()
                .filter(newsletter -> newsletter.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Newsletter não encontrado!")
                );
    }
}
