package com.kamila.food.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer // Configura a aplicação como Authorization Server
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    private DataSource dataSource;

    // Configura os clients que vão acessar o Resource Server
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Client é a aplicação que utiliza os recursos do Resource Server utilizando um Access Token
        clients.jdbc(dataSource);
    }

    // Configura acesso ao endpoint de chacagem do token
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // isAuthenticated() é uma Spring Security Expression
        // Afirma que é necessário o client estar autenticado para acessar o endpoint check token
        // security.checkTokenAccess("isAuthenticated()");

        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients(); // Permite passar as credenciais do cliente no corpo da requisição
    }


    /* Especifica um Authentication Manager para o Password Flow.
     * Somente o Password Flow necessita, através dele o Authorization Server valida o usuário/senha
     * passado na autenticação via API.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        var enhancerChain = new TokenEnhancerChain();
        // O TokenEnhancer customizado deve vir primeiro na lista para ser reconhecido
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhance(), jwtAccessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                // Adiciona informaçoes (claims) ao token
                .tokenEnhancer(enhancerChain)
                // Habilita opção de escolha granular de 'scopes' do usuário. Deve ser chamado após accessTokenConverter()
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
    }


    public ApprovalStore approvalStore(TokenStore tokenStore) {
        var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }


    /*
     * Converte as informações de um usuário logado para JWT e vice-versa.
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // Algoritmo: Mac SHA 256 simétrica.
        // Para ser válido um token deve ter a mesma key configurada aqui, inserida na sua assinatura.
        // jwtAccessTokenConverter.setSigningKey("0DEF419417A1C1B371E29B143D49D6F707D8FC008EC55E73270D1647F3CD2054"); // A chave deve ser complexa e secreta

        // Algoritmo RSA SHA-256, chave Assimétrica

        var keyStorepass = jwtKeyStoreProperties.getPassword(); // Senha para abrir arquivo jks
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias(); // Apelido de identificação do par de chaves, pois dentro do arquivo pode haver mais de um par

        var keyStoreKeyFactory = new KeyStoreKeyFactory(
                jwtKeyStoreProperties.getJksLocation(), keyStorepass.toCharArray());
        var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, // PKCE
                endpoints.getTokenGranter()); // Demais fluxos granters implementados

        return new CompositeTokenGranter(granters);
    }

}
