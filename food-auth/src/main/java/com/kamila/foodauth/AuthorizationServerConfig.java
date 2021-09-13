package com.kamila.foodauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer // Configura a aplicação como Authorization Server
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Configura os clients que vão acessar o Resource Server
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Client é a aplicação que utiliza os recursos do Resource Server utilizando um Access Token
        clients.inMemory()
                .withClient("food-web") // Identificação do client
                .secret(passwordEncoder.encode("web123")) // Senha
                .authorizedGrantTypes("password") // Fluxos
                .scopes("write", "read")
                .accessTokenValiditySeconds(60 * 60 * 6); // 6 horas
    }

    // Configura acesso ao endpoint de chacagem do token
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // isAuthenticated() é uma Spring Security Expression
        // Afirma que é necessário o client estar autenticado para acessar o endpoint check token
        // security.checkTokenAccess("isAuthenticated()");

        security.checkTokenAccess("permitAll()");
    }

    /* Especifica um Authentication Manager para o Password Flow.
     * Somente o Password Flow necessita, através dele o Authorization Server valida o usuário/senha
     * passado na autenticação via API.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

}
