package com.general.purpose.oauth.provider;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.general.purpose.oauth.core.UserDetails;
import com.general.purpose.oauth.remote.user.model.User;
import com.general.purpose.oauth.remote.user.service.UserService;
import com.general.purpose.oauth.service.UserDetailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Implementation of a custom Authentication Provider for providing User authentication check.
 * <p>
 * Every user (not client) will trigger {@link #authenticate(Authentication)} where the verification will be handle.
 * <p>
 * {@link UserDetailService} will be used to retrieve the user.
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Configuration
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    private static final String TENANT_DETAIL_KEY = "tenantId";

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final UserService userService;

    /**
     * Checks user credentials.
     *
     * @param authentication the authentication request object.
     *
     * @return fully {@link Authentication} object including credentials.
     * @throws AuthenticationException  if authentication fails.
     * @throws IllegalArgumentException if the object is <code>null</code>
     * @since 1.0.0
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Validate.notNull(authentication);

        if(!((Map) authentication.getDetails()).containsKey(TENANT_DETAIL_KEY)) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Tenant id must be provided within the token request!");
        }
        Set<User> users = userService.findByEmail(authentication.getName());
        String requestedTenant = (String) ((Map) authentication.getDetails()).get(TENANT_DETAIL_KEY);
        Optional<User> userOptional = users.stream()
                                           .filter(u -> u.getTenantId().equals(UUID.fromString(requestedTenant)))
                                           .findFirst();

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with email %s has not been found!", authentication.getName())));

        String pass = user.getPassword();
        String rawPassword = (String) authentication.getCredentials();
        if(!passwordEncoder.matches(rawPassword, pass)) {
            throw new BadCredentialsException("Wrong password");
        }

        UserDetails userDetails = UserDetails.builder().name(user.getName()).email(user.getEmail()).id(user.getId())
                                             .role(user.getRole()).tenantId(user.getTenantId()).password("").build();

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userDetails.getRole());
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    /**
     * <p>Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the indicated
     * <Code>Authentication</code> object.
     * <p>
     * <p>Returning <code>true</code> does not guarantee an <code>AuthenticationProvider</code> will be able to
     * authenticate the presented instance of the <code>Authentication</code> class. It simply indicates it can support
     * closer evaluation of it. An <code>AuthenticationProvider</code> can still return <code>null</code> from the
     * {@link #authenticate(Authentication)} method to indicate another <code>AuthenticationProvider</code> should be tried.
     *
     * @param authentication Authentication class
     *
     * @return <code>true</code> if the implementation can more closely evaluate the <code>Authentication</code> class presented
     * @since 1.0.0
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
