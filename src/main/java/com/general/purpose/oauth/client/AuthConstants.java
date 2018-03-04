package com.general.purpose.oauth.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>Common constants.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_AUTH_TYPE = "Bearer";
    public static final String BASIC_AUTH_TYPE = "Basic";
    public static final String BEARER_AUTHORIZATION_TEMPLATE = BEARER_AUTH_TYPE + " %s";

}
