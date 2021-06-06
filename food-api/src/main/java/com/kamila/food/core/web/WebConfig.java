package com.kamila.food.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Habilita acesso a qlqr origin em todo o Projeto
                .allowedMethods("*"); // Por padrão estão aqui métodos simples: get, head e post
//                .allowedOrigins("http://www.food.local")
//                .maxAge(30);
    }
}
