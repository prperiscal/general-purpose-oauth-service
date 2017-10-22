package com.staks.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Convenient strategy for configuring OAuth2 Authorization Server.
 * <p>
 * This beans will be applied to the Spring context automatically by using {@link EnableAuthorizationServer @EnableAuthorizationServer}
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    private static final String OAUTH = "OAuth-service";
    private static final String GRANT_TYPE = "client-grant";

    /**
     * Add configuration for clients.
     * <p>
     * A client is a reference to a resourceService with a Role.
     *
     * @param clients in-memory client list
     *
     * @throws Exception If there was a problem while loading clients
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(OAUTH).authorizedGrantTypes(GRANT_TYPE).autoApprove(true);
    }
}
