package com.general.purpose.oauth.client;

import static com.general.purpose.oauth.client.AuthConstants.AUTHORIZATION_HEADER;
import static com.general.purpose.oauth.client.AuthConstants.BASIC_AUTH_TYPE;
import static java.lang.String.format;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * <p>Class to createClientTokenRequest {@link HttpEntity <String>} to request client access token.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ClientTokenRequestFactory {

    private static final String REQUEST_BODY = "grant_type=client_credentials";
    private static final String AUTHORIZATION_HEADER_TEMPLATE = BASIC_AUTH_TYPE + " %s";
    private static final String PLAIN_CREDENTIALS_TEMPLATE = "%s:%s";

    @NonNull
    private final ClientAuthenticationProperties properties;

    /**
     * <p>Creates http entity that's required in access token request.</p>
     *
     * @return http entity
     */
    public HttpEntity<String> createClientTokenRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION_HEADER, getAuthorization());

        return new HttpEntity<>(REQUEST_BODY, headers);
    }

    private String getAuthorization() {
        return format(AUTHORIZATION_HEADER_TEMPLATE, getBase64Credentials());
    }

    private String getBase64Credentials() {
        String plainCredentials = format(PLAIN_CREDENTIALS_TEMPLATE, properties.getId(), properties.getSecret());
        return Base64.encodeBase64String(plainCredentials.getBytes());
    }

}
