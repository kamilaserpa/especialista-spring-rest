# Food Analytics

Simulação de aplicação cliente que solicitará autenticação de um usuário permitindo acesso aos recursos de Food API através do fluxo de autenticação **Authorization Code Grant Type**.

### Start

 - Build: `npm install`
 - Start: `node client.js`
A aplicação será executada na porta 3000 por padrão.
Para efetuar login utilize um usuário como os listados na classe [WebSecurityConfig](food-auth\src\main\java\com\kamila\foodauth\WebSecurityConfig.java) em food-auth.

 - Rotas: "/localhost:3000" e "/localhost:3000/pkce".

### Fluxo Authorization Code Grant Type

![Fluxo AUthorization Code](../food-api/images/authorization_code.png)
