package com.general.purpose.oauth.converter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.general.purpose.oauth.core.UserDetails;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.CollectionUtils;

/**
 * <p>Converter to user a custom principal object inside the authentication object.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
public class UserAuthenticationConverter implements org.springframework.security.oauth2.provider.token.UserAuthenticationConverter {

    private static final String USER_DETAILS = "user_details";
    private static final String PROP_ID = "id";
    private static final String PROP_TENANT_ID = "tenantId";
    private static final String PROP_NAME = "name";
    private static final String PROP_EMAIL = "email";
    private static final String PROP_ROLE = "role";

    /**
     * <p>Extract information about the user to be used in an access token (i.e. for resource servers).
     *
     * @param authentication an authentication representing a user.
     *
     * @return a map of key values representing the unique information about the user.
     * @throws IllegalArgumentException if authentication is <code>null</code>
     * @since 1.0.0
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Validate.notNull(authentication, String.format("The validated object '%s' is null", "authentication"));

        Map<String, Object> result = Maps.newHashMap();
        result.put(USERNAME, authentication.getName());
        result.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        result.put(USER_DETAILS, extractMap((UserDetails) authentication.getPrincipal()));
        return result;
    }

    /**
     * <p>Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an Authentication from a map.
     *
     * @param map a map of user information.
     *
     * @return an Authentication representing the user or null if there is none.
     * @throws NullPointerException if authenticationAsMap is <code>null</code>
     * @since 1.0.0
     */
    @Override
    @SuppressWarnings("unchecked")
    public Authentication extractAuthentication(final Map<String, ?> map) {
        Validate.notNull(map, String.format("The validated object '%s' is null", "authenticationAsMap"));

        String userName = (String) map.get(USERNAME);
        Map<String, Object> userDetailsMap = (Map<String, Object>) map.get(USER_DETAILS);

        if(StringUtils.isEmpty(userName) || CollectionUtils.isEmpty(userDetailsMap)) {
            return null;
        }

        List<String> authorities = (List<String>) map.get(AUTHORITIES);
        if(CollectionUtils.isEmpty(authorities)) {
            return null;
        }

        UserDetails userDetails = extractUserDetails(userDetailsMap);
        List<GrantedAuthority> grantedAuthorities = extractGrantedAuthorities(authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
    }

    private static UserDetails extractUserDetails(Map<String, Object> userDetailsMap) {
        return UserDetails.builder().id(UUID.fromString(userDetailsMap.get(PROP_ID).toString()))
                          .tenantId(UUID.fromString(userDetailsMap.get(PROP_TENANT_ID).toString()))
                          .name(userDetailsMap.get(PROP_NAME).toString()).password("")
                          .email(userDetailsMap.get(PROP_EMAIL).toString())
                          .role(userDetailsMap.get(PROP_ROLE).toString()).build();
    }

    private static Map<String, Object> extractMap(UserDetails userDetails) {
        Map<String, Object> result = Maps.newHashMap();
        result.put(PROP_ID, userDetails.getId());
        result.put(PROP_TENANT_ID, userDetails.getTenantId());
        result.put(PROP_NAME, userDetails.getName());
        result.put(PROP_EMAIL, userDetails.getEmail());
        result.put(PROP_ROLE, userDetails.getRole());
        return result;
    }

    private static List<GrantedAuthority> extractGrantedAuthorities(Collection<String> authorities) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", authorities));
    }

}