package com.kamila.food.core.openapi;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

    // Registrando uma instância de Docket (sumário) como um Componente Spring
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(Predicates.and( //Documenta controllers dos pacotes especificados
//                        RequestHandlerSelectors.basePackage("com.kamila.food.api"),
//                        RequestHandlerSelectors.basePackage("com.kamila.food.api.outro"))
//                )
//                Documenta controllers do pacote específico
                .apis(RequestHandlerSelectors.basePackage("com.kamila.food.api"))
                .build()
                .tags(new Tag("Cidades", "Gerencia as cidades"))
                .apiInfo(apiInfo());
    }

    /*
    Informações do cabeçalho da página http://localhost:8080/swagger-ui.html
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("KamilaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Food", "http://www.food.com", "contato@food.com"))
                .build();
    }

    // Mapeamento de caminhos para servir arquivos estáticos do Swagger UI
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
