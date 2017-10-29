package com.mytasks.oauth.config;

import com.mytasks.oauth.service.UserDetailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Convenient strategy for configuring OAuth2 Authorization Server.
 * <p>
 * This beans will be applied to the Spring context automatically by using {@link EnableAuthorizationServer @EnableAuthorizationServer}
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@RequiredArgsConstructor
@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    private static final String OAUTH = "OAuth-service";
    private static final String WEB = "WebClient";
    private static final String ANDROID = "AndroidApp";
    private static final String IOS = "IosApp";
    private static final String CLIENT_CREDENTIALS_GRANT = "client_credentials";
    private static final String PASSWORD_GRANT = "password";
    private static final String REFRESH_TOKEN_GRANT = "refresh_token";
    private static final String SERVICE_SCOPE = "service";
    private static final String CLIENT_SCOPE = "client";

    @NonNull
    private final TokenStore tokenStore;

    @NonNull
    private final AccessTokenConverter accessTokenConverter;

    @NonNull
    private final AuthenticationManager authenticationManagerBean;

    @NonNull
    private final UserDetailService userDetailService;

    /**
     * Add configuration for clients.
     * <p>
     *
     * @param clients in-memory client list
     *
     * @throws Exception If there was a problem while loading clients
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //@formatter:off
        clients.inMemory()
               .withClient(WEB)    .authorizedGrantTypes(PASSWORD_GRANT, REFRESH_TOKEN_GRANT).scopes(CLIENT_SCOPE).autoApprove(true).and()
               .withClient(ANDROID).authorizedGrantTypes(PASSWORD_GRANT, REFRESH_TOKEN_GRANT).scopes(CLIENT_SCOPE).autoApprove(true).and()
               .withClient(IOS)    .authorizedGrantTypes(PASSWORD_GRANT, REFRESH_TOKEN_GRANT).scopes(CLIENT_SCOPE).autoApprove(true).and()
               .withClient(OAUTH)  .authorizedGrantTypes(CLIENT_CREDENTIALS_GRANT).scopes(SERVICE_SCOPE).autoApprove(true);
        //@formatter:on
    }

    /**
     * Configure the security of the Authorization Server, which means in practical terms the /oauth/token endpoint. The
     * /oauth/authorize endpoint also needs to be secure, but that is a normal user-facing endpoint and should be
     * secured the same way as the rest of your UI, so is not covered here.
     *
     * @param security a fluent configurer for security features
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * Set our extended configuration.
     * <ul>
     * <li>JWT configuration will be set by the tokenStore {@link JwtConfig#accessTokenConverter()}</li>
     * <li>The user authentication provider by authenticationManager {@link ServerSecurityConfig#authenticationManagerBean()}</li>
     * <li>The user details service by userDetailsService {@link UserDetailService}</li>
     * </ul>
     *
     * @param endpoints Configure the properties and enhanced functionality of the Authorization Server endpoints.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).accessTokenConverter(accessTokenConverter)
                 .authenticationManager(authenticationManagerBean).userDetailsService(userDetailService);
    }
}
