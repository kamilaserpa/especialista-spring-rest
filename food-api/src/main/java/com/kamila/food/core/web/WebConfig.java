package com.kamila.food.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Habilita acesso a qlqr origin em todo o Projeto
                .allowedMethods("*"); // Por padrão estão aqui métodos simples: get, head e post
//                .allowedOrigins("http://www.food.local")
//                .maxAge(30);
    }

    /**
     * Ao retornar uma resposta de requisição, ele gera um hash da resposta e insere no cabeçalho Etag
     * e verifica se o hash coincide com o header If-non-match.
     * Caso seja igual, a requsição retorna apenas 304
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
