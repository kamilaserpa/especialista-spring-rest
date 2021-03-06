package com.kamila.food.core.springfox;

import com.fasterxml.classmate.TypeResolver;
import com.kamila.food.api.exceptionhandler.Problem;
import com.kamila.food.api.v1.model.*;
import com.kamila.food.api.v1.openapi.model.*;
import com.kamila.food.api.v2.model.CidadeModelV2;
import com.kamila.food.api.v2.model.CozinhaModelV2;
import com.kamila.food.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.kamila.food.api.v2.openapi.model.CozinhasModelV2OpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    // Registrando uma inst??ncia de Docket (sum??rio) como um Componente Spring
    @Bean
    public Docket apiDocketV1() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kamila.food.api"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .useDefaultResponseMessages(false) // Desabilitando httpStatus codes error padr??o
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//                .globalOperationParameters(Arrays.asList(
//                        new ParameterBuilder()
//                                .name("campos")
//                                .description("Nomes das propriedades para filtrar na resposta, separados por v??rgula")
//                                .parameterType("query")
//                                .modelRef(new ModelRef("string")) // Tipo do model
//                                .build()
//                ))
                .additionalModels(typeResolver.resolve(Problem.class)) // Adicionando Modelo extra para ser exibido em Models
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // Para exibir os par??metros recebidos em um Pageable
                .directModelSubstitute(Links.class, LinksModelOpenApi.class) // Substituindo Links do HATEOAS
                // Substituindo um Page<CozinhaModel> para CozinhasModelOpenApi
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModel.class),
                        CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModel.class),
                        CidadesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
                        EstadosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
                        FormasPagamentoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoModel.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
                        PermissoesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
                        ProdutosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
                        RestaurantesBasicoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
                        UsuarioModelOpenApi.class))

                // Documenta????o sobre autentica????o e seguran??a
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))

                .apiInfo(apiInfoV1())
                .ignoredParameterTypes(
                        ServletWebRequest.class, // Par??metro injetado pelo Spring, n??o inserido pelo usu??rio, desnecess??rio na doc
                        URL.class, URI.class, URLStreamHandler.class, // Ignorando a descri????o destes tipos de "Model"
                        Resource.class, File.class, InputStream.class)
                .tags(new Tag("Cidades", "Gerencia as cidades"))
                .tags(new Tag("Grupos", "Gerencia os grupos de usu??rios"))
                .tags(new Tag("Cozinhas", "Gerencia as cozinhas"))
                .tags(new Tag("Formas de pagamento", "Gerencia as formas de pagamento"))
                .tags(new Tag("Pedidos", "Gerencia os pedidos"))
                .tags(new Tag("Restaurantes", "Gerencia os restaurantes"))
                .tags(new Tag("Estados", "Gerencia os estados"))
                .tags(new Tag("Produtos", "Gerencia os produtos"))
                .tags(new Tag("Usu??rios", "Gerencia os usu??rios"))
                .tags(new Tag("Estat??sticas", "Estat??sticas do Food"))
                .tags(new Tag("Permiss??es", "Gerencia as permiss??es"));
    }

    @Bean
    public Docket apiDocketV2() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kamila.food.api"))
                .paths(PathSelectors.ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false) // Desabilitando httpStatus codes error padr??o
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class)) // Adicionando Modelo extra para ser exibido em Models
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // Para exibir os par??metros recebidos em um Pageable
                .directModelSubstitute(Links.class, LinksModelOpenApi.class) // Substituindo Links do HATEOAS

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
                        CidadesModelV2OpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CozinhaModelV2.class),
                        CozinhasModelV2OpenApi.class))

                .apiInfo(apiInfoV2())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Cozinhas", "Gerencia as Cozinhas"));
    }

    // Descreve t??cnicas de seguran??a utilizadas para proteger API. OAuth ?? um Security Scheme.
    public SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("KamilaFood")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    // Descreve os caminhos da API que est??o protegidos
    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("KamilaFood") // Referencia o Scheme de seguran??a utilizado (mesmo nome do securityScheme acima)
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(PathSelectors.any()) // Para quais paths o SecurityScheme ser?? utilizado (todos)
                .build();
    }

    // GrantTypes disponibilizados para utiliza????o pela documenta????o
    private List<GrantType> grantTypes() {
        return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    // Lista de Scopes dispon??veis para utiliza????o pela p??gina de documenta????o
    private List<AuthorizationScope> scopes() {
        return Arrays.asList(
                new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita"));
    }

    // Lista de C??digos de status de erro Globais que ser??o exibidos na Documenta????o (Problem.class)
    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.OK.value())
                        .message("Consulta realizada com sucesso") // SpringFox substitui pelo que est?? espec??fico em cada m??todo
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema")) // Referenciando o modelo de resposta em caso 400 de GetMethod
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso n??o possui representa????o aceita pelo consumidor")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisi????o inv??lida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso n??o possui representa????o aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private List<ResponseMessage> globalPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisi????o inv??lida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso n??o possui representa????o aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisi????o inv??lida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    /*
    Informa????es do cabe??alho da p??gina http://localhost:8080/swagger-ui.html
     */
    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("KamilaFood API (Depreciada)")
                .description("API aberta para clientes e restaurantes. <br>" +
                        "<strong>Essa vers??o da API est?? depreciada e deixar?? de existir a partir de 01/01/20XX." +
                        "Use a vers??o mais atual da API.</strong>")
                .version("1")
                .contact(new Contact("Food", "http://www.food.com", "contato@food.com"))
                .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("KamilaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
                .contact(new Contact("Food", "http://www.food.com", "contato@food.com"))
                .build();
    }

    // Mapeamento de caminhos para servir arquivos est??ticos do Swagger UI
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
