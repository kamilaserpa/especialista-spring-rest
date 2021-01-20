# Especialista Spring Rest
![Spring](https://static.computerworld.com.pt/media/2010/09/Spring-Logo.png)

Curso Especialista Spring da AlgaWorks.

## Capítulo 2 - Spring e Injeção de Dependências

#### @Component
Notifica que a classe é um Bean gerenciado pelo Spring. Durante a varredura de componentes, o bootstrap da aplicação, o Spring Framework detecta automaticamente as classes anotadas com @Component e as instancia.
Por padrão, as instâncias de bean desta classe têm o mesmo nome que o nome da classe com uma inicial minúscula.
@Repository , @Service , @Configuration e @Controller são todas meta-anotações de @Component.

#### @Controller
`@Controller`também é um componente gerenciado pelo Spring, no caso, que responde requisições Web.
Essa anotação de nível de classe informa ao Spring Framework que essa classe serve como um controlador no Spring MVC.

#### @Retention
Usamos a anotação `@Retention` para dizer em que parte do ciclo de vida do programa nossa anotação se aplica.
Para fazer isso, precisamos configurar @Retention com uma das três políticas de retenção:

```java
RetentionPolicy.SOURCE - visível nem pelo compilador nem pelo tempo de execução
RetentionPolicy.CLASS - visível pelo compilador
RetentionPolicy.RUNTIME - visível pelo compilador e pelo tempo de execução
```

#### @Profile
Permite mapear beans para diferentes perfis. Considere um cenário básico: temos um bean que deve estar ativo apenas durante o desenvolvimento, mas não implantado na produção. Apenas Beans anotados com `@Profile("dev")` seriam instanciados caso esteja configurado o perfil de desenvolvimento em application.properties `spring.profiles.active=dev`.

#### @Bean
No Spring, os objetos que formam o backbone de seu aplicativo e que são gerenciados pelo contêiner Spring IoC são chamados de beans. Um bean é um objeto instanciado, montado e gerenciado de outra forma por um contêiner Spring IoC (Inversion of Control).

#### @Configuration
As classes de configuração podem conter métodos de definição de bean anotados com `@Bean`.

#### Ciclo de vida dos Beans
Inicialização, execução, destruição. Após contrutor e importações é chamado o init (`@PostConstructor`).

#### Propriedades
- Substituindo propriedades via linha de comando, por exemplo:
`java -jar target/food-api.jar --server.port=8081`
- Substituindo variáveis por variável de ambiente:

	- `EXPORT SERVER_PORT=8082` (macOs)
	- `set SERVER_PORT=8081
		echo %SERVER_PORT%`(Windows)
- Ativando o Spring Profile por linha de comando:
`java -jar target/food-api.jar --spring.profiles.active=development`
- Ativando o Spring Profile por variável de ambiente:
	- `set SPRING_PROFILES_ACTIVE=production` (Windows)

#### @ConfigurationProperties
Essa anotação auxilia na configuração externalizada e facilita acesso às propriedades definidas nos arquivos de propriedades
O Spring vinculará automaticamente qualquer propriedade definida em nosso arquivo de propriedade que tenha o prefixo "notificador.email" e o mesmo nome de um dos campos na classe `NotificadorProperties`.

## Capítulo 3 - Introdução ao JPA e Hibernate

#### EntityManager
EntityManager é uma parte da Java Persistence API. Principalmente, ele implementa as interfaces de programação e regras de ciclo de vida definidas pela especificação JPA 2.0.

#### @Transactional
Podemos anotar um bean com @Transactional no nível da classe ou do método. A anotação também oferece suporte a outras configurações:

- o tipo de propagação da transação
- o nível de isolamento da transação
- um tempo limite para a operação envolvida pela transação
- um sinalizador readOnly - uma dica para o provedor de persistência de que a transação deve ser somente leitura
- as regras de reversão para a transação
Observe que - por padrão, a reversão acontece para o tempo de execução, exceções não verificadas apenas. A exceção verificada não dispara um rollback da transação.

#### Estados de uma entidade
Uma entidade pode assumir alguns estados com relação ao EntityManager. Os estados podem ser:

- Novo (new ou transient)
- Gerenciado (managed) - através dos métodos `persist`, `merge` ou buscar a entidade usando o EntityManager
- Removido (removed) - método `remove`
- Desanexado (detached) - método `detach`

Observar que não é possível um objeto em estado *transient* ir direto para o estado *removed*. Por isso a entidade foi buscada, para ficar em estado gerenciado, e só após isso é chamado o método *removed*.

![Diagrama de estados JPA](https://s3.amazonaws.com/algaworks-blog/wp-content/uploads/Diagrama-de-estados.png)

#### Padrão DDD - Domain-Driven Design
Design Orientado a Domínio representa um grupo de objetos de domínio que podem ser tratados como uma única unidade. Um exemplo pode ser um pedido e seus itens de pedido, eles serão objetos separados, mas é útil tratar o pedido (junto com seus itens de linha) como um único agregado.
<https://martinfowler.com/bliki/DDD_Aggregate.html>

Não se deve criar repositórios para entidades que não são agregate root, no exemplo o agregate root é o pedido.

#### Dialeto SQL
 Ao inserir relacionamento entre entidades é necessário inserir o dialeto para que o hibernate adicione a *foreign key* ao criar a coluna anotada com o relacionamento: `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect`.

#### Propriedade nullable de @Column e @JoinColumn
Indica que ao criar tabela através da aplicação, os campos serão ou não anuláveis no banco de dados.

## Capítulo 4 - REST com Spring

#### Constraints do REST
 - Cliente-servidor
 - Stateless
 - Cache
 - Interface uniforme
 - Sistema em camadas
 - Código sob demanda

#### REST *vs* RESTful
REST é o estilo arquitetural que segue as constraints do REST, é a especificação. RESTful é uma API desenvolvida em conformidade com as constraints REST.

#### Recursos REST

Coisas expostas na web, possui importância para ser referenciado como uma coisa no software. Pode ser Singleton (representa uma única coisa) ou Collection.
Rest usa URIs(Uniform Resource Identifier) para identificar um recurso.

URI vs URL -  URL (Uniform Resource Locator) é um tipo de URI, especifica a localização do recurso (com protocolo por exemplo). Ex.: https://market.com.br/produtos. O plural é consenso de utilização.
URI deve referenciar à alguma coisa, um substantivo e não a um verbo ou ação, coisas possuem propriedades, verbos não.

#### @ResponseBody
A resposta do método deve ser enviada como resposta da requisição HTTP. `@RestController`engloba as anotations _@Controller_ e _@ResponseBody_.

#### Negociação de conteúdo
Para realização de content negotiation o cliente afirma qual formato de conteúdo ele aceita através do cabeçalho `Accept` com um MediaType, por exemplo application/json, application/xml, etc.

Ao adicionar dependência jackson a api passa a responder as requisições tanto com json quanto com xml.
```xml
<dependency>
	<groupId>com.fasterxml.jackson.dataformat</groupId>
	<artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```
Ao definir o *MediaType* em um método específico, este passa a responder apenas o tipo configurado, respondendo com `406 Not Acceptable`. Também é possível definir qual método é chamado a partir do tipo de negociação de conteúdo.
`@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })`

 - Status HTTP para collection resource vazia: 200
 - Status HTTP para singleton resource inexistente: 404. Indica erro do cliente, por exemplo a url `cozinhas/9999` não retorna nenhum recurso, "não existe", já `cozinhas/2` existe.

#### SerialVersionUID
Ele é o recurso que usamos para dizer ao Java que um objeto serializado é compatível ou não com o .class utilizado para desserializar.
Dentro da [especificação](https://docs.oracle.com/javase/8/docs/platform/serialization/spec/class.html#a4100) existe uma nota recomendando que os desenvolvedores declarem a propriedade explicitamente para evitar problemas de desserialização.

## Capítulo 5 - Spring Data JPA
JPQL é a linguagem de consultas do JPA.

#### Keywords e Query Methods
Mecanismo de criação de queries por meio de palavras chave: Distinct, And, Or, Containing, Between, OrderBy, Null, After, etc.
[Documentação do Spring Data JPA:Keywords de querymethods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)

Deve começar por "find", mas pode começar também por "read", "get", "Query" ou "stream".
 - Prefixos de query methods: *count, top, first, exists*, etc.

---

##### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.12.RELEASE/maven-plugin/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)

##### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

#### Developer
[Kamila Serpa](https://kamilaserpa.github.io)
