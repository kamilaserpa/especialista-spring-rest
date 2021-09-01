package com.kamila.food.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ApiRetirementHandler apiRetirementHandler;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Habilita acesso a qlqr origin em todo o Projeto
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("*"); // Por padrão estão aqui métodos simples: get, head e post
//                .allowedOrigins("http://www.food.local")
//                .maxAge(30); // Tempo (segundos) q o navegador pode armazenar resposta do preflight em cache
    }

    /**
     * Define versão padrão da API caso não seja especificado MediaType pelo client
     * no header Accept.
     */
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.defaultContentType(FoodMediaTypes.V2_APPLICATION_JSON);
//    }

    /**
     * Ao retornar uma resposta de requisição, ele gera um hash da resposta e insere no cabeçalho Etag
     * e verifica se o hash coincide com o header If-non-match.
     * Caso seja igual, a requisição retorna apenas 304.
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    /* Adiciona Interceptador das requests */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiRetirementHandler);
    }

}
