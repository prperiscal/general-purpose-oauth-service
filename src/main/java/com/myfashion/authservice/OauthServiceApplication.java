package com.myfashion.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @EnableResourceServer will turn our application into a resource server
 * (enables Spring Scurity filter to authenticate requests via an incoming OAuth2 token).
 */
@EnableAuthorizationServer
@SpringBootApplication
public class OauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }
}
