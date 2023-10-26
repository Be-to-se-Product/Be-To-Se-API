package com.be.two.c.apibetwoc.model.observer;


import com.be.two.c.apibetwoc.model.Newsletter;
import com.be.two.c.apibetwoc.service.EnviadorEmailService;

public interface Assinante {

    void receberNewsletter(EnviadorEmailService enviadorEmailService, Newsletter newsletter);
}