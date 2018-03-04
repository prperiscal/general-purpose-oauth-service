package com.general.purpose.oauth.client;


import javax.annotation.PostConstruct;

import com.general.purpose.oauth.client.exception.GenericFeignInterceptorException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Class that is responsible for retrieving and refreshing client access token.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AccessTokenProvider {

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final ClientTokenRequestFactory clientTokenRequestFactory;

    @NonNull
    private final ClientAuthenticationProperties properties;

    private HttpEntity<String> clientTokenRequest;

    private AccessToken accessToken;
    private long tokenReceivedTimeInSec;

    @PostConstruct
    public void init() {
        this.clientTokenRequest = clientTokenRequestFactory.createClientTokenRequest();
    }

    /**
     * <p>Returns client access token.
     *
     * @return access token
     */
    public AccessToken getAccessToken() {
        if(accessToken == null || isTokenExpired()) {
            tokenReceivedTimeInSec = System.currentTimeMillis() / 1000;
            accessToken = retrieveAccessToken();
        }
        return accessToken;
    }

    private boolean isTokenExpired() {
        return tokenReceivedTimeInSec + accessToken.getExpiresIn() < System.currentTimeMillis() / 1000;
    }

    private AccessToken retrieveAccessToken() {
        try {
            return restTemplate.postForEntity(properties.getAccessTokenUrl(), clientTokenRequest, AccessToken.class)
                               .getBody();
        } catch (Exception e) {
            throw new GenericFeignInterceptorException(e);
        }
    }
}
