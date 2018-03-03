package com.general.purpose.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * {@link EnableAuthorizationServer @EnableAuthorizationServer} will turn our application into a oauthrization server.
 * <p>
 * {@link EnableDiscoveryClient @EnableDiscoveryClient} activates the Netflix Eureka DiscoveryClient implementation.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 */
@EnableAuthorizationServer
@EnableDiscoveryClient
@SpringBootApplication
public class OauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }
}
