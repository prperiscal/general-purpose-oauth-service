package com.general.purpose.oauth.client;

import static com.general.purpose.oauth.client.AuthConstants.AUTHORIZATION_HEADER;
import static com.general.purpose.oauth.client.AuthConstants.BEARER_AUTHORIZATION_TEMPLATE;
import static java.lang.String.format;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <p>Interceptor that adds Authorization header with a client access token to each feign request.
 *
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AuthorizeFeignRequestInterceptor implements RequestInterceptor {

    @NonNull
    private final AccessTokenProvider accessTokenProvider;

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header(AUTHORIZATION_HEADER,
                        format(BEARER_AUTHORIZATION_TEMPLATE, accessTokenProvider.getAccessToken().getValue()));
    }
}
