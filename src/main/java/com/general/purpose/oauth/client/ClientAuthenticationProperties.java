package com.general.purpose.oauth.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Properties for enable feign security use.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "general.purpose.oauth.client")
public final class ClientAuthenticationProperties {

    /**
     * The client identification.
     */
    private String id;

    /**
     * The secret phrase of the client.
     */
    private String secret;

    /**
     * The url to retrieve the access token of the client.
     */
    private String accessTokenUrl;

}
