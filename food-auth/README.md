# Authorization Server

Projeto Authorization Server utilizado pela **Food API** (Resource Server).
Poderia ser o mesmo projeto, opção de escolha.

## Projeto Migrado
A partir da aula 23.41 o projeto Authorization Server foi migrado para a <b>Food API</b>, sendo realizada a autenticação na própria Food API, funcionando esta como Resource Server e Authentication Server.

Para autenticação apenas Food API é necessária a partir de então. Por isso a classe principal `AUthApplication` foi anotada com `@Deprecated`, porém plenamente funcional.
