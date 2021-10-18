package com.kamila.food.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

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
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(apiRetirementHandler);
//    }

}
