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

#### Criteria
`CriteriaQuery` é responsável por criar a estrutura de uma query, a composição das cláusulas.
`CriteriaBuilder` funciona como uma fábrica que contrói elementos para a construção da consulta.
Não vale a pena ser utilizada para consultas simples por ser mais verbosa e demandar mais esforço programático.

#### Specification
Filtros são as *specifications* de forma mais isolada, de modo que podem ser combinados.
Ponto negativo, possibilidade de usar o mesmo specification em vários lugares, caso seja necessária alguma alteração, a implementação seria ajustada em vários locais do código. Possível solução, isolar a combinação de specifications em um método na classe RepositoryImpl.

#### Customizando o repositório Base
Estendendo *JpaRepository*, utilizando *Generics*, importando *EntityManager* é possível criar um repositório base customizado, com os métodos do JPA e outros mais.
Necessário inserir a anotação na classe main:

`@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)`

## Capítulo 6 - JPA e Hibernate

#### @Embeddable
JPA fornece a  anotação @Embeddable para declarar que uma classe será integrada por outras entidades.

```java
@Embeddable // Entidade incorporável
public class Endereco {
```

#### @Embedded
A anotação JPA @Embedded é usada para embutir um tipo em outra entidade. Incorporando para uma única tabela de banco de dados, no exemplo em *tb_restaurante*.

```java
public class Restaurante {
	//...

	@Embedded
	private Endereco endereco;
```
#### @CreationTimestamp
Marca uma propriedade como o carimbo de data/hora ao salvar a entidade pela primeira vez. Anotação da implementação Hibernate.

#### @UpdateTimestamp
Marca uma propriedade como o carimbo de data/hora ao atualizar uma entidade. Anotação da implementação Hibernate. O próprio Hibrenate insere o valor do atibuto.

#### Eager Loading
Carregamento "ansioso", antecipado. Todas as associações terminados em _"ToOne"_ utilizam a estratégia `Eager Loading`. Ainda com @JsonIgnore o select de entidade mappeada é realizado, por exemplo as buscar um restaurante específico, é feito select de cozinhas realizadas, por causa da estratégia EagerLoading. Ou seja, quando uma entidade é carregada no banco de dados as entidades associadas a ela também serão carregadas.
Não significa que a associação Eager será feita em um select apenas através _joins_, pode gerar vários selects. "Problema" "n+1", uma busca na verdade origina mais *n* consultas.

#### Lazy Loading
Carregamento preguiçoso. Todas as associações terminados em _"ToMany"_ utilizam a estratégia `Lazy Loading`. Não realiza consultas de entidade associadas sem que algum método dessa entidade seja explicitamente chamado.
Sem o `@JsonIgnore` é preciso analisar pois a consulta da entidade provalvelmente será realizada carregando os abjetos associados, apenas não estaria serializando para json no retorno das requisições.

## Capítulo 7 - Pool de conexões e Flyway

`spring.jpa.hibernate.ddl-auto=update` cria uma coluna, porém alterações posteriores, como nullable, alteração do nome da coluna não são realizadas pois não garantem a manutenão do funcionamento da aplicação, logo `Schema generation` em produção não é uma boa prática. O próprio Hibernate [afirma](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#schema-generation).

> Embora a geração automática de esquema seja muito útil para fins de teste e prototipagem, em um ambiente de produção, é muito mais flexível gerenciar o esquema usando scripts de migração incremental.

#### Flyway
Necessário inserir arquivos *sql* na pasta `resources/db/migration` com nome de versão, podendo ter pontos, underlines ou data hora, iniciando por V maiúsculo, por exemplo: V1.0.sql, V1_0.sql, V001__descricao.sql, V20212801.
É criada a tabela `flyway_schema_history` com auditoria de alterações.

As alterações são incrementais, da versão 1 para a 4, são necessários executar a v2, em seguida v3 e só após a v4. Deve ser evitado inserir inserts de dados de teste em *migrations* para evitar que estes valores entrem em produção.

Passos sugeridos, criar arquivo .sql dentro da pasta *db/migration* apenas com uma descrição, como "cria-tabela-usuario.sql". Em seguida inserir os comandos sql no arquivo, e então renomear inserindo a versão, com V maiúsculo e número da versão.
Verifica a inalteração de versão arquivo sql anteriormente adicionado pelo campo *checksum*.

Para alterações maiores em banco sugere-se criar um backup/dump do banco de dados em desenvolvimento. Em seguida escrever o script e executar para verificar o funcionamento, após isso restaurar o backup realizado anteriormente, e copiar os comandos *sql* para o arquivo migration dentro do projeto Spring.
Não se deve inserir dados de teste no banco através de arquivos migration sql.

##### Inserção de dados
Dados de testepodem ser inseridos no banco através do arquivo `afterMigrate.sql`. Podemos inserir este arquivo em pasta específica e indicar ao Flyway a leitura nessa pasta a través de propriedade em *application*:

	> spring.flyway.locations=classpath:db/migration,classpath:db/testdata

##### Falha
Quando uma migração **falha** ela fica armazenada na tabela de auditoria criada pelo Flyway chamada *flyway_schema_history*, com o valor 0 na coluna "success". Ainda que seja corrigida, o Flyway não permite a aplicação de uma versão já executada. Portanto deve ser observado até onde o arquivo foi executado, essas alterações devem ser desfeitas, o arquivo corrigido e o comportamento (em tese/desenvolvimento) seria deletar essa tupla em *flyway_schema_history*. Corrigir o arquivo sql migration e executar novamente o projeto.

Outra opção para remover a ultima tupla de *flyway_schema_history* seria executar na pasta do projeto o comando `./mvnw flyway:repair -Dflyway.configFiles=src/main/resources/flyway.properties`. Sendo criado o arquivo mencionado com os dados de conexão com o banco. 

*Se você copiar (da raiz do nosso projeto, ou qualquer projeto gerado pelo Spring Initializr) os arquivos mvnw, mvnw.cmd e a pasta .mvn você será capaz de executar os comandos maven de dentro do diretório onde colocou esses arquivos.*

## Capítulo 8 - Tratamento e modelagem de erros da API

A anotação `@ResponseStatus(HttpStatus.NO_CONTENT)` acima do método no Controller indica o status http retornado caso não hajam exceções.
Exceção ainda retornada com atributos: "timestamp", "status", "error", "message", "trace".
As classes de domínio não devem ter contato com a camada que se relaciona com a web, os controllers têm essa responsabilidade, como por exemplo inserir código de status de resposta Http.

`ResponseStatusException` indicada para projetos menores, quando se deseja que uma única exceção retorne diferentes status Http, quando não tem possui tempo hábil para criação de exceções customizadas. Com esse objeto ainda não é possível customizar corpo da resposta de exceção, apenas status code e mensagem.
Uma exceção personalizada pode estender `ResponseStatusException`, como vantagem têm-se a possibilidade de enviar diferentes códigos de status Http por quem lança a exception, temos uma exceção que pode retornar diversos status Http. Desvantagem é caso a exceção esteja dentro de uma classe serviço, então o código http seria definido em um pacote que deve conter regras de negócio.

Importante ter um padrão de objeto de resposta de exceções em uma API.

Exemplo de especificações que tentam padronizar o formato de resposta com os detalhes do erro como: jsonapi.org, vnd.error, [Problem Details for Http APIs](https://tools.ietf.org/html/rfc7807) (RFC 7807, IETF). Neste projeto será usado a última especificaçao:

```json
{
    "status": 400,
    "type": "https://foodkamila.combr/recurso-em-uso.",
    "title": "Recurso em uso",
    "detail": "Não foi possível excluir a cozinha de código 8, porque está em uso",
    "instance": "/cozinhas/8/erros/98204983"
}]
```

Propriedade *status* deve ter o mesmo código de status da requisição original gerado pela API. *Type* é uma URI que identifica o tipo do problema, pode ou não ser uma URL acessível pela internet, sendo um a URI que descreve um problema único e como resolvê-lo. *Title* descreve um texto legível para humanos que descreve o erro, deve ser o mesmo para mesmo código de statusHttp de erro.
*Detail* é uma descrição mais detalhada do erro específico legível para humanos. *Instance* é um apropriedade opcional onde conta uma URI exata específica do erro retornado.`

##### Podemos expor detalhes internos do erro, como a stacktrace?
Pode-se adicionar a pilha de erros à resposta por opção de escolha. Porém em APIs públicas, onde terceiros podem acessar, não é recomendado retornar detalhes internos da API, pois essa informação não é útil para o cliente e pode expor dados sensíveis da aplicação para terceiros.


##### Todas as respostas com erros precisam ter um corpo descrevendo o problema?
Não, pois há problemas já bem documentados pelo protocolo Http. Interessante retornar o *status* e *title* pelo menos.

##### Benefícios
Formato único para descrver erros, de forma que o consumidor possa entender o que aconteceu e tomar uma decisão. Facilita a construção e manutenção da API. Caso o cliente utilize mais de uma API, se elas utilizaerem o mesmo padrão de resposta de erro não é necessário tratamentos específicos para compreensão dessa resposta.

Deve-se analisar se a excepção que ocorreu é um subtipo de outra exceção. Caso positivo, deve-se tratar a exceção mais genérica.

## Capítulo 9 - Validações com Bean Validation

A partir da versão 2.3.0 do Spring o Bean Validation não é adicionado automaticamente como dependência do pacote spring-boot-starter-web. Nesse caso é necessário adicionar a dependência do spring-boot-starter-validation no projeto de forma explícita:

```xml
<dependency> 
    <groupId>org.springframework.boot</groupId> 
    <artifactId>spring-boot-starter-validation</artifactId> 
</dependency>
```
Apenas inserindo critério de validação na entidade, está é implementada no repository. Para validar no controller deve ser inserido `@Valid` no objeto recebido no controller.


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
