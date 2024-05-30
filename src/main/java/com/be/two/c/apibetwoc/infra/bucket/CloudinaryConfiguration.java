package com.be.two.c.apibetwoc.infra.bucket;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("prod")
public class CloudinaryConfiguration {
    @Value("${cloudinary.url}")
    private String url;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(url);
    }

}