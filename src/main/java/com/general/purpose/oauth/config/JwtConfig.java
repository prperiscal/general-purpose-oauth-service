package com.general.purpose.oauth.config;

import com.general.purpose.oauth.converter.UserAuthenticationConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * <p>Configuration class to createClientTokenRequest jwt tokens
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    @NonNull
    private final KeyProperties keyProperties;

    /**
     * <p>Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * <p>Helper that translates between JWT encoded token values and OAuth authentication
     * information (in both directions). Also acts as a {@link TokenEnhancer} when tokens are
     * granted.
     * <p>
     * <p>This one configured with asymmetric key.
     *
     * @see TokenEnhancer
     * @see AccessTokenConverter
     */
    public JwtAccessTokenConverter accessTokenConverterAsymmetric() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("1234");
        return jwtAccessTokenConverter;
    }

    /**
     * <p>Helper that translates between JWT encoded token values and OAuth authentication
     * information (in both directions). Also acts as a {@link TokenEnhancer} when tokens are
     * granted.
     * <p>
     * <p>This one configured with Public-private key.
     *
     * @see TokenEnhancer
     * @see AccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(accessTokenConverter());
        ClassPathResource classPathResource = new ClassPathResource(keyProperties.getKeyFileName());
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,
                                                                       keyProperties.getKeyPassword().toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(keyProperties.getKeyPair()));
        return converter;
    }

    /**
     * <p>Default implementation of {@link AccessTokenConverter}.
     * <p>Included with custom converter
     */
    public AccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userTokenConverter());
        return accessTokenConverter;
    }

    /**
     * <p>Custom bean for converting a user authentication to and from a Map.
     */
    public UserAuthenticationConverter userTokenConverter() {
        return new UserAuthenticationConverter();
    }
}
