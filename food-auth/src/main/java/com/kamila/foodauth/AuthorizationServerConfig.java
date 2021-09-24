package com.kamila.foodauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    private UserDetailsService userDetailsService;

    // Configura os clients que vão acessar o Resource Server
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Client é a aplicação que utiliza os recursos do Resource Server utilizando um Access Token
        clients.inMemory()
                .withClient("food-web") // Identificação do client (aplicação web ou mobile)
                .secret(passwordEncoder.encode("web123")) // Senha
                .authorizedGrantTypes("password", "refresh_token") // Fluxos
                .scopes("write", "read")
                .accessTokenValiditySeconds(6 * 60 * 60) // 6 horas
                .refreshTokenValiditySeconds(60 * 24 * 60 * 60) // 60 dias

                .and()
                    .withClient("foodanalytics") // Identifica aplicação back-end que consulta a API
                    .secret(passwordEncoder.encode("food123")) // Senha
                    .authorizedGrantTypes("authorization_code") // Fluxos
                    .scopes("write", "read")
                    .redirectUris("http://aplicacao-cliente")

                .and()
                    .withClient("faturamento") // Identifica aplicação back-end que consulta a API
                    .secret(passwordEncoder.encode("faturamento123")) // Senha
                    .authorizedGrantTypes("client_credentials") // Fluxos
                    .scopes("write", "read")

                .and()
                    .withClient("checktoken") // Identificação do Resource Server (Food API)
                    .secret(passwordEncoder.encode("check123"));
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
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false);
    }

}
